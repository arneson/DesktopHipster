package desktopHipster;

import java.awt.HeadlessException;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;

import org.junit.Test;

import view.DDFrame;
import view.DragNDropTray;
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
	public boolean testDropNull(){
		DDFrame testFrame = new DDFrame();
		try{
			testFrame.drop(new DropTargetDropEvent(null, null, 0, 0));
			return true;
		}catch (Exception e){
			return false;
		}
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
		DDFrame testFrame = new DDFrame();
		if(testFrame.isAcceptedImage(new File(getClass().getResource("/robin.png").getPath())))
			return true;
		return false;		
	}
	@Test
	public boolean testTrayIcon(){
		try{
			DragNDropTray ddtray = new DragNDropTray();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
}
