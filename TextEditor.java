//package p1;
import java.io.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
//import p1.Customizer;
//import p1.FileSetter;
//import p1.FileOperation
//import p1.Decorate

public class TextEditor  implements ActionListener, MenuConstants
{

JFrame f;
JTextArea ta;

private String fileName="Untitled";
private boolean saved=true;
String applicationName="Text Editor";

String searchString, replaceString;
int lastSearchIndex;

FileOperation fileHandler;
Customizer fontDialog=null;
JColorChooser bcolorChooser=null;
JColorChooser fcolorChooser=null;
JDialog backgroundDialog=null;
JDialog foregroundDialog=null;
JMenuItem cutItem,copyItem, deleteItem, findItem, findNextItem, replaceItem, selectAllItem, readItem;

TextEditor()
{
f=new JFrame(fileName+" - "+applicationName);
ta=new JTextArea(30,60);
f.add(new JScrollPane(ta),BorderLayout.CENTER);

f.add(new JLabel("  "),BorderLayout.EAST);
f.add(new JLabel("  "),BorderLayout.WEST);
createMenuBar(f);

f.pack();
f.setLocation(100,50);
f.setVisible(true);
f.setLocation(150,50);
f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

fileHandler=new FileOperation(this);


DocumentListener myListener = new DocumentListener()
{
public void changedUpdate(DocumentEvent e){fileHandler.saved=false;}
public void removeUpdate(DocumentEvent e){fileHandler.saved=false;}
public void insertUpdate(DocumentEvent e){fileHandler.saved=false;}
};
ta.getDocument().addDocumentListener(myListener);


WindowListener frameClose=new WindowAdapter()
{
public void windowClosing(WindowEvent we)
{
if(fileHandler.confirmSave())System.exit(0);
}
};
f.addWindowListener(frameClose);
}

public void actionPerformed(ActionEvent ev)
{
String cmdText=ev.getActionCommand();

if(cmdText.equals(fileNew))
	fileHandler.newFile();
else if(cmdText.equals(fileOpen))
	fileHandler.openFile();

else if(cmdText.equals(fileSave))
	fileHandler.saveThisFile();

else if(cmdText.equals(fileSaveAs))
	fileHandler.saveAsFile();


else if(cmdText.equals(filePermission))
{
JCheckBoxMenuItem temp=(JCheckBoxMenuItem)ev.getSource();
fileHandler.setPermission(temp);
}



else if(cmdText.equals(fileExit))
	{if(fileHandler.confirmSave())System.exit(0);}



else if(cmdText.equals(editCut))
	ta.cut();

else if(cmdText.equals(editCopy))
	ta.copy();

else if(cmdText.equals(editPaste))
	ta.paste();

else if(cmdText.equals(editDelete))
	ta.replaceSelection("");


else if(cmdText.equals(editSelectAll))
	ta.selectAll();


else if(cmdText.equals(formatFont))
{
if(fontDialog==null)
	fontDialog=new Customizer(ta.getFont());

if(fontDialog.showDialog(TextEditor.this.f,"Choose a font"))
TextEditor.this.ta.setFont(fontDialog.createFont());
}

else if(cmdText.equals(formatForeground))
	showForegroundColorDialog();

else if(cmdText.equals(formatBackground))
	showBackgroundColorDialog();


else if(cmdText.equals(helpAboutTextEditor))
{
JOptionPane.showMessageDialog(TextEditor.this.f,aboutText,"Text Editor Details",JOptionPane.INFORMATION_MESSAGE);
}
else
	
	return;
}

void showBackgroundColorDialog()
{
if(bcolorChooser==null)
	bcolorChooser=new JColorChooser();
if(backgroundDialog==null)
	backgroundDialog=JColorChooser.createDialog
		(TextEditor.this.f,
		formatBackground,
		false,
		bcolorChooser,
		new ActionListener()
		{public void actionPerformed(ActionEvent evvv){
			TextEditor.this.ta.setBackground(bcolorChooser.getColor());}},
		null);		

backgroundDialog.setVisible(true);
}

void showForegroundColorDialog()
{
if(fcolorChooser==null)
	fcolorChooser=new JColorChooser();
if(foregroundDialog==null)
	foregroundDialog=JColorChooser.createDialog
		(TextEditor.this.f,
		formatForeground,
		false,
		fcolorChooser,
		new ActionListener()
		{public void actionPerformed(ActionEvent evvv){
			TextEditor.this.ta.setForeground(fcolorChooser.getColor());}},
		null);		

foregroundDialog.setVisible(true);
}


JMenuItem createMenuItem(String s, int key,JMenu toMenu,ActionListener al)
{
JMenuItem temp=new JMenuItem(s,key);
temp.addActionListener(al);
toMenu.add(temp);

return temp;
}

JMenuItem createMenuItem(String s, int key,JMenu toMenu,int aclKey,ActionListener al)
{
JMenuItem temp=new JMenuItem(s,key);
temp.addActionListener(al);
temp.setAccelerator(KeyStroke.getKeyStroke(aclKey,ActionEvent.CTRL_MASK));
toMenu.add(temp);

return temp;
}

JCheckBoxMenuItem createCheckBoxMenuItem(String s, int key,JMenu toMenu,ActionListener al)
{
JCheckBoxMenuItem temp=new JCheckBoxMenuItem(s);
temp.setMnemonic(key);
temp.addActionListener(al);
temp.setSelected(false);
toMenu.add(temp);

return temp;
}

JMenu createMenu(String s,int key,JMenuBar toMenuBar)
{
JMenu temp=new JMenu(s);
temp.setMnemonic(key);
toMenuBar.add(temp);
return temp;
}

void createMenuBar(JFrame f)
{
JMenuBar mb=new JMenuBar();
JMenuItem temp;

JMenu fileMenu=createMenu(fileText,KeyEvent.VK_F,mb);
JMenu editMenu=createMenu(editText,KeyEvent.VK_E,mb);
JMenu formatMenu=createMenu(formatText,KeyEvent.VK_O,mb);

JMenu helpMenu=createMenu(helpText,KeyEvent.VK_H,mb);

createMenuItem(fileNew,KeyEvent.VK_N,fileMenu,KeyEvent.VK_N,this);
createMenuItem(fileOpen,KeyEvent.VK_O,fileMenu,KeyEvent.VK_O,this);
createMenuItem(fileSave,KeyEvent.VK_S,fileMenu,KeyEvent.VK_S,this);
createMenuItem(fileSaveAs,KeyEvent.VK_A,fileMenu,this);
readItem=createCheckBoxMenuItem(filePermission,KeyEvent.VK_W,fileMenu,this);
readItem.setEnabled(false);

fileMenu.addSeparator();
createMenuItem(fileExit,KeyEvent.VK_X,fileMenu,this);

temp=createMenuItem(editUndo,KeyEvent.VK_U,editMenu,KeyEvent.VK_Z,this);
temp.setEnabled(false);
editMenu.addSeparator();
cutItem=createMenuItem(editCut,KeyEvent.VK_T,editMenu,KeyEvent.VK_X,this);
copyItem=createMenuItem(editCopy,KeyEvent.VK_C,editMenu,KeyEvent.VK_C,this);
createMenuItem(editPaste,KeyEvent.VK_P,editMenu,KeyEvent.VK_V,this);
deleteItem=createMenuItem(editDelete,KeyEvent.VK_L,editMenu,this);
deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));

editMenu.addSeparator();
selectAllItem=createMenuItem(editSelectAll,KeyEvent.VK_A,editMenu,KeyEvent.VK_A,this);


createMenuItem(formatFont,KeyEvent.VK_F,formatMenu,this);
formatMenu.addSeparator();
createMenuItem(formatForeground,KeyEvent.VK_T,formatMenu,this);
createMenuItem(formatBackground,KeyEvent.VK_P,formatMenu,this);




temp=createMenuItem(helpHelpTopic,KeyEvent.VK_H,helpMenu,this);
temp.setEnabled(false);
helpMenu.addSeparator();
createMenuItem(helpAboutTextEditor,KeyEvent.VK_A,helpMenu,this);

MenuListener editMenuListener=new MenuListener()
{
   public void menuSelected(MenuEvent evvvv)
	{
	if(TextEditor.this.ta.getText().length()==0)
	{
	selectAllItem.setEnabled(false);
	readItem.setEnabled(true);
	
	}
	else
	{
	selectAllItem.setEnabled(true);
	readItem.setEnabled(true);

	
	}
	if(TextEditor.this.ta.getSelectionStart()==ta.getSelectionEnd())
	{
	cutItem.setEnabled(false);
	copyItem.setEnabled(false);
	deleteItem.setEnabled(false);
	}
	else
	{
	cutItem.setEnabled(true);
	copyItem.setEnabled(true);
	deleteItem.setEnabled(true);
	}
	}
   public void menuDeselected(MenuEvent evvvv){}
   public void menuCanceled(MenuEvent evvvv){}
};
editMenu.addMenuListener(editMenuListener);
f.setJMenuBar(mb);
}

public static void main(String[] s)
{
new TextEditor();
}
}


interface MenuConstants
{
final String fileText="File";
final String editText="Edit";
final String formatText="Customize";


final String helpText="Help";

final String fileNew="New";
final String fileOpen="Open...";
final String fileSave="Save";
final String fileSaveAs="Save As...";
final String filePermission = "Set Read Only Permissions";

final String fileExit="Exit";

final String editUndo="Undo";
final String editCut="Cut";
final String editCopy="Copy";
final String editPaste="Paste";
final String editDelete="Delete";

final String editSelectAll="Select All";

final String formatFont="Set Font & Size";
final String formatForeground="Set Text color...";
final String formatBackground="Set Background color...";



final String helpHelpTopic="Help Topic";
final String helpAboutTextEditor="About this Text Editor";

final String aboutText=
	"<html>Your Text Editor<hr><hr>"
	+"<p align=center>Team members: Aarushi Agarwal, Aliyah Kabeer, Anwesha Kelkar"
	+"<hr><p align=center>This was done as a part of our course Object Oriented Design & Analysis with Java<br><br>"
	+"<hr><em>Bye!</em><hr><html>";
}