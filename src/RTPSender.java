import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;

import javax.media.CannotRealizeException;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoProcessorException;
import javax.media.NotRealizedError;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.format.AudioFormat;
import javax.media.format.UnsupportedFormatException;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.rtp.InvalidSessionAddressException;
import javax.media.rtp.RTPManager;
import javax.media.rtp.SendStream;
import javax.media.rtp.SessionAddress;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sun.media.rtp.RTPSessionMgr;


public class RTPSender {

	private SendStream sendStream;
	private String ip, url;
	private int port;
	private MediaLocator mediaLocator = null;
	private DataSink dataSink = null;
	private Processor mediaProcessor = null;
	private static final Format[] FORMATS = new Format[] {new AudioFormat(AudioFormat.ULAW_RTP)}; /* Input format of processor */
	private static final ContentDescriptor CONTENT_DESCRIPTOR = new ContentDescriptor(ContentDescriptor.RAW_RTP); /* Output format of processor*/
	private File file;

	public RTPSender(String ip, int port) throws NoDataSourceException, MalformedURLException, IOException, NoProcessorException, CannotRealizeException, InvalidSessionAddressException, UnsupportedFormatException{
		this.ip = ip;
		this.port = port;

		/* Load WavFile*/
		file = new File("currentmessage.wav");
		@SuppressWarnings("deprecation")
		DataSource ds = Manager.createDataSource(new MediaLocator(file.toURL()));
		
		/* Declare MediaProcessor*/
		mediaProcessor = Manager.createRealizedProcessor(new ProcessorModel(ds, FORMATS, CONTENT_DESCRIPTOR));
		mediaProcessor.configure();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		mediaProcessor.setContentDescriptor(CONTENT_DESCRIPTOR);
		mediaProcessor.start();
		mediaProcessor.getTrackControls()[0].setFormat(FORMATS[0]);
		
		/* Declare media processor to convert wav format to suitable RTP format */
		/* Datasink is our output block */
		/* Medialocator is the destination of the datasink*/

		RTPManager rtpManager = RTPManager.newInstance();
		SessionAddress localAdr = new SessionAddress();
		InetAddress ipAddress = InetAddress.getByName(ip);
		rtpManager.initialize(localAdr);
		
		/* Specify remoteAddress to send stream to*/
		SessionAddress remoteAddress = new SessionAddress(ipAddress, port);
		rtpManager.addTarget(remoteAddress); /* Add that remoteaddress to target*/
		
		
		/* Create the send stream towards the remoteAddress*/
		sendStream = rtpManager.createSendStream(mediaProcessor.getDataOutput(), 0);		
		return;
	}
	
	/* Method for starting and stopping RTP transmission */
	public void startRTP() throws IOException{
		sendStream.start();
		
	}
	
	public void stopRTP() throws IOException{
		sendStream.stop();
		
	}
	
	public long getFileLengthInSeconds(){
		long length;
		length = file.length()/8;
		System.out.println("length of the current message in milisec is: " + length);
		return length;
	}
}
