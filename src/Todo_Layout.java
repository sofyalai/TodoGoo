import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class Todo_Layout extends JFrame {

	private static final long serialVersionUID = 4247632372991742918L;
	private Container cp;
	private JButton upButton;
	/*
	 * 
	 * private JCheckBox viewAll; private JTextField outPut;
	 */
	private JPanel cpan;
	private JPanel npan;
	private JPanel span;

	private JComboBox listComboBox;

	private DefaultComboBoxModel mdl;

	private JButton addButton, downButton, removeButton;
	private int current_sheetIndex, error;

	private JButton save;

	private All_TaskItems all_taskItems; // serializable class
	private File backUp = new File("o.ser");

	private int selected_item_Index;

	@SuppressWarnings("unchecked")
	public Todo_Layout() throws ClassNotFoundException {

		setTitle("TODO");
		setSize(310, 470);
		setLocation(300, 300);

		/*
		 * open with the back up file and restore data to program if "o.ser"
		 * exists or create a new task object
		 */
		backUp = new File("o.ser");
		if (!backUp.exists()) {
			all_taskItems = new All_TaskItems();
		} else {
			try {
				ObjectInputStream inputStream = new ObjectInputStream(
						new FileInputStream("o.ser"));
				all_taskItems = (All_TaskItems) inputStream.readObject();
				for (int i = 0; i < all_taskItems.get_allList().size(); i++) {
					for (int j = 0; j < all_taskItems.get_allList().get(i)
							.size(); j++) {
						all_taskItems.get_allList().get(i).get(j)
								.register_taskitem_Done(); // re-register object
															// to listeners
					}
				}

				inputStream.close();

			} catch (IOException e) {

			}
		}

		upButton = new JButton("up");
		downButton = new JButton("dn");
		removeButton = new JButton("rm");
		/*
		 * viewAll = new JCheckBox("ViewAll"); outPut = new JTextField(10);
		 */

		addButton = new JButton("add");

		cpan = new JPanel();
		npan = new JPanel();

		mdl = new DefaultComboBoxModel(all_taskItems.get_sheetsList().toArray());
		listComboBox = new JComboBox(mdl);

		cp = getContentPane();
		cp.setLayout(new BorderLayout());

		npan.setLayout(new GridLayout(1, 2, 10, 20));
		npan.add(listComboBox);

		// npan.add(viewAll);
		// npan.add(outPut);
		span = new JPanel(new GridLayout(1, 5, 2, 20));
		span.add(addButton);
		span.add(upButton);
		span.add(downButton);
		span.add(removeButton);

		cpan.setLayout(new FlowLayout());

		cpan.setBackground(Color.WHITE);
		cp.add(cpan, BorderLayout.CENTER);
		cp.add(span, BorderLayout.SOUTH);

		cp.add(npan, BorderLayout.NORTH);

		// register

		upButton.addActionListener(new upButtonListener());
		addButton.addActionListener(new AddButtonListener());
		downButton.addActionListener(new downButtonListener());
		removeButton.addActionListener(new removeButtonListener());
		listComboBox.addActionListener(new ListsHandler());

		// viewAll.addItemListener(new viewAllHandler());

		/*
		 * Save the Serializable object "all_taskItems" to "o.ser" file which
		 * keeps all status changed before window is closed
		 */
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				ObjectOutputStream outStream = null;
				try {
					outStream = new ObjectOutputStream(new FileOutputStream(
							"o.ser"));
					System.out.println("write");
					outStream.writeObject(all_taskItems);
					outStream.close();
				} catch (IOException e1) {

					e1.printStackTrace();
				}

				System.exit(0);

			}
		});

	}
	/*
	 * add one taskitem into the selected work sheet
	 */
	private class AddButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			all_taskItems.get_allList().get(current_sheetIndex)
					.add(new TaskItem());

			putTaskItems(current_sheetIndex);
			System.out.println("add"
					+ all_taskItems.get_allList().get(current_sheetIndex)
							.size());

		}
	}
	
	/*
	 * move up the selected taskitem 
	 */
	private class upButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (selected_item_Index > 0) {
				TaskItem temp_item = all_taskItems.get_allList()
						.get(current_sheetIndex).get(selected_item_Index);
				all_taskItems.get_allList().get(current_sheetIndex)
						.remove(selected_item_Index);
				all_taskItems.get_allList().get(current_sheetIndex)
						.add(selected_item_Index - 1, temp_item);
				--selected_item_Index;
			}

			putTaskItems(current_sheetIndex);
		}
	}
	/*
	 * move down the selected taskitem
	 */
	private class downButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (selected_item_Index < all_taskItems.get_allList()
					.get(current_sheetIndex).size() - 1) {
				TaskItem temp_item = all_taskItems.get_allList()
						.get(current_sheetIndex).get(selected_item_Index);
				all_taskItems.get_allList().get(current_sheetIndex)
						.remove(selected_item_Index);
				all_taskItems.get_allList().get(current_sheetIndex)
						.add(selected_item_Index + 1, temp_item);
				++selected_item_Index;
			}

			putTaskItems(current_sheetIndex);
		}
	}
	/*
	 * remove the selected taskitem
	 */
	private class removeButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			all_taskItems.get_allList().get(current_sheetIndex)
					.remove(selected_item_Index);
			putTaskItems(current_sheetIndex);
		}
	}

	/*
	 * Functions of the "Manage List", "Add work sheet" and 
	 * switch between work sheets
	 */
	private class ListsHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (listComboBox.getSelectedIndex() == 0) {
				// outPut.setText("Manage List");
			} else if (listComboBox.getSelectedIndex() == 1) {
				String sheet_name;
				boolean confirm = true;

				do {
					error = 0;
					try {
						sheet_name = JOptionPane
								.showInputDialog("Enter a new name :");
						if (sheet_name == null) {
							confirm = false;
							break;
						}

						if (sheet_name.length() == 0)
							throw new Exception("Name cannot be empty");
						else
							all_taskItems.get_sheetsList().setName(sheet_name);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
						error = 1;
					}
				} while (error == 1);

				if (confirm) {
					all_taskItems.get_sheetsList().addList();

					// reset JComboBox model
					DefaultComboBoxModel mdl_2 = new DefaultComboBoxModel(
							all_taskItems.get_sheetsList().toArray());
					listComboBox.setModel(mdl_2);

					// create one sheet and one task
					all_taskItems.get_allList().add(new ArrayList<TaskItem>());

					current_sheetIndex = all_taskItems.get_allList().size() - 1;
					all_taskItems.get_allList().get(current_sheetIndex)
							.add(new TaskItem());

					listComboBox
							.setSelectedIndex(listComboBox.getItemCount() - 1);
					putTaskItems(current_sheetIndex);
					System.out.println(current_sheetIndex);

					cp.validate();
				}

			} else if (listComboBox.getSelectedIndex() == 2) {

			} else {
				// put the TaskItems of the sheet
				current_sheetIndex = listComboBox.getSelectedIndex() - 3;
				putTaskItems(current_sheetIndex);

			}
		}
	}

	public void putTaskItems(int sheet_index) {
		int i;

		cpan.removeAll();
		for (i = 0; i < all_taskItems.get_allList().get(sheet_index).size(); i++) {
			TaskItem t = (TaskItem) all_taskItems.get_allList()
					.get(sheet_index).get(i);
			final int temp_i = i;
			// t.registerItemPanel();
			t.get_taskitem_Text().addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					selected_item_Index = temp_i;
				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

			});

			cpan.add(t);

		}

		cpan.repaint(); // important
		cp.validate();

	}

	/*
	 * private class viewAllHandler implements ItemListener { public void
	 * itemStateChanged(ItemEvent e) { if (e.getStateChange() ==
	 * ItemEvent.SELECTED) outPut.setText("view all"); else
	 * outPut.setText("not view all"); } }
	 */
}
