package util;

import javax.swing.JOptionPane;

public class ShowMessageUtil {
    /*
     *  提示信息弹窗，多个地方调用
     *  故放入Util(工具)包，并static修饰，方便调用
     */
    public static void winMessage(String str) {
	JOptionPane.showMessageDialog(null, str, "提示",
		JOptionPane.INFORMATION_MESSAGE);
    }
}
