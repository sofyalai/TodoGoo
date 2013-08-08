import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.io.Serializable;
import java.text.AttributedString;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;

public class TaskItem extends JPanel {
	private String taskitem_Name;
	private JCheckBox taskitem_Done;
	private JButton taskitem_Info;
	private JTextArea taskitem_Text;
	private Date taskStartdate;
	private Date taskDuedate;
	private String taskRemark;

	
	
	
    
    public TaskItem() {
		init();
		taskitem_Name = "init";

	}

	public TaskItem(String n) {
		set_taskitem_Name(n);

		init();
	}

	public void init() {
		taskStartdate = new Date();
				
		this.setLayout(new BorderLayout(0, 0));
		
		taskitem_Text = new JTextArea(1, 20);
		taskitem_Text.setLineWrap(true);
		
		taskitem_Info = new JButton("->");
		taskitem_Done = new JCheckBox();
		taskitem_Done.setBackground(Color.white);
		
		taskitem_Text.setText(get_taskitem_Name());
		
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));	    
		this.add(taskitem_Text, BorderLayout.CENTER);
		this.add(taskitem_Info, BorderLayout.EAST);
		this.add(taskitem_Done, BorderLayout.WEST);
		
		
		//register
		//taskitem_Done.addItemListener(new taskitem_DoneHandler());
		register_taskitem_Done();
		//registerItemPanel();
		taskitem_Text.addMouseListener(new MouseHandler());	
		taskitem_Info.addMouseListener(new MouseHandler());	
		taskitem_Done.addMouseListener(new MouseHandler());	
		
	}
	
	public void register_taskitem_Done(){
		taskitem_Done.addItemListener(new ItemListener(){
			Map attribute = taskitem_Text.getFont().getAttributes();
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					//Map attribute = taskitem_Text.getFont().getAttributes();
					attribute.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
					Font newFont = new Font(attribute);
					taskitem_Text.setFont(newFont);
					System.out.println("selected");
					
				}else{
					attribute.remove(TextAttribute.STRIKETHROUGH);
					Font newFont = new Font(attribute);
					taskitem_Text.setFont(newFont);
				}
				taskitem_Text.validate();
			}
			
		});
		
	}
	
	private class MouseHandler implements MouseListener {
		public void mouseClicked(MouseEvent e) {					
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			taskitem_Done.setBackground(Color.lightGray);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			taskitem_Done.setBackground(Color.white);
			
		}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}




	public void set_taskitem_Name(String n) {
		taskitem_Name = n;
	}

	public String get_taskitem_Name() {
		return taskitem_Name;
	}

	
	public JCheckBox get_taskitem_Done(){
		return taskitem_Done;
	}
	public JTextArea get_taskitem_Text(){
		return taskitem_Text;
	}
	
}
