//package p1;
import java.io.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ReadPermission implements Permissions{
	@Override public void setPerm(File temp)
	{

		System.out.println("Read permission granted");
		temp.setWritable(false);
	}
}