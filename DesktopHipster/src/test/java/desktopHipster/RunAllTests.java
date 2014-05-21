package desktopHipster;

import org.junit.Test;

public class RunAllTests {

	@Test
	public void main() {
		DragAndDropTest ddtest = new DragAndDropTest();
		ddtest.main();
		TagPanelTest tpTest = new TagPanelTest();
		tpTest.main();

	}

}
