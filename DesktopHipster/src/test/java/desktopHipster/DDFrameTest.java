package desktopHipster;

import java.awt.HeadlessException;
import java.io.File;

import org.junit.Test;

import dragNdrop.DDFrame;
public class DDFrameTest {
	@Test
	public boolean testDropImage(){
		try{
			DDFrame testFrame = new DDFrame();
			if(testFrame.isAcceptedImage(new File(getClass().getResource("/robin.jpg").getPath())))
				return true;
		}
		catch (HeadlessException he){
			return false;
		}
		return false;
	}
	@Test
	public boolean testDropNotImage(){
		try{
			DDFrame testFrame = new DDFrame();
			if(testFrame.isAcceptedImage(new File(getClass().getResource("/robin.txt").getPath())))
				return true;
		}
		catch (HeadlessException he){
			return false;
		}
		return false;
	}
	
	@Test
	public boolean testDropNotImageWithImageExtension(){
		try{
			DDFrame testFrame = new DDFrame();
			if(testFrame.isAcceptedImage(new File(getClass().getResource("/robin.png").getPath())))
				return true;
		}
		catch (HeadlessException he){
			return false;
		}
		return false;		
	}
	
}
