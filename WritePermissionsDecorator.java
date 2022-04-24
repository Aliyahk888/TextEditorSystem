//package p1;
import java.io.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class WritePermissionsDecorator extends PermissionsDecorator{
	public WritePermissionsDecorator(Permissions decoratedPermissions)
	{
		super(decoratedPermissions);
	}

	@Override public void setPerm(File temp)
	{
		decoratedPermissions.setPerm(temp);
		setWritePermissions(decoratedPermissions, temp);
	}


	private void setWritePermissions(Permissions decoratedPermissions, File temp)
	{
		temp.setWritable(true);
		System.out.println("Write permission granted");

	}

}
