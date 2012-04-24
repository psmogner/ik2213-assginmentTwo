import javax.sound.sampled.AudioFileFormat.Type;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

/* Class for managing FREETTS to be able to create a wav file containing the chosen text msg as a sound message*/
public class VoiceCreator {

	Voice speekerVoice;
	AudioPlayer audioplayer = null;
	
	public VoiceCreator(){
		
		String voiceName = "kevin"; // Use kevin voice
		VoiceManager vm = VoiceManager.getInstance();
		speekerVoice = vm.getVoice(voiceName);
		if(speekerVoice == null){
			System.out.println("Error when creating voice");
		}
	}
	
	/* Create a new wav file containing the chosen message sent to this method*/
	public void createVoiceFile(String msg){
		speekerVoice.allocate();
		audioplayer = new SingleFileAudioPlayer("currentmessage", Type.WAVE);
		speekerVoice.setAudioPlayer(audioplayer);
		speekerVoice.speak(msg);
		speekerVoice.deallocate();
		audioplayer.close();
	}
}
