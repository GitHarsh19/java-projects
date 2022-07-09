package javarecorder;
import javax.sound.sampled.*;
import java.io.*;
import java.util.*;


public class javarecorder
{
    public static void main(String[] args)
    {
    	
        try 
        {	
        	Scanner sc = new Scanner(System.in);
        	AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100,16,2,4,44100,true);
        	DataLine.Info dataInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        	TargetDataLine targetLine = (TargetDataLine)AudioSystem.getLine(dataInfo);
        	targetLine.open();
        	System.out.println("recording has started");
        	targetLine.start();
        	File audioFile = new File("record.wav");
        	Thread thread = new Thread()
        	{
        		@Override public void run() 
        		{
        			AudioInputStream astream = new AudioInputStream(targetLine);
        			try { AudioSystem.write(astream, AudioFileFormat.Type.WAVE,audioFile);
        			}
        			catch(IOException ex) 
        			{
        				System.out.println(ex);
        			}
        			System.out.println("recording has stopped");
        		}
        	};
        	thread.start();
        	Thread.sleep(6000);
        	targetLine.stop();
        	AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        	Clip clip = AudioSystem.getClip();
        	clip.open(audioStream);
        	String reply = "";
        	while(!reply.equals("Q")) 
        	{
        	System.out.println("P = play, S = Stop, R = Reset, Q = Quit");
        	System.out.print("Enter your choice: ");
        	reply = sc.next();
        	reply=reply.toUpperCase();
        	switch(reply) {
            case ("P"): clip.start();
            break;
            case ("S"): clip.stop();
            break;
            case ("R"): clip.setMicrosecondPosition(0);
            break;
            case ("Q"): clip.close();
            break;
            default: System.out.println("Not a valid response");
            break;
        	}
        	}
        	targetLine.close();
        }
        catch(Exception e)
        {
        	System.out.println(e);
        }
    }
}