//package p1;
import java.io.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public abstract class PermissionsDecorator implements Permissions{
	protected Permissions decoratedPermissions;

	public PermissionsDecorator(Permissions decoratedPermissions)
	{
		this.decoratedPermissions=decoratedPermissions;
	}

	public void setPerm(File temp) {
		decoratedPermissions.setPerm(temp);
	}
}
