import java.util.ArrayList;

	
	
public class SheetList extends ArrayList<String> {
	private String listName;	
	public SheetList(){
		
		this.add("Manage List");
		this.add("Add List");
		this.add("\t");
		
		
	}
	
		
	public void addList(){
		String n = getName();
		this.add(n);
		
	}
	public int getSheetListSize(){
		
		return this.size();
		
	}

	public boolean setName(String n){
		if(n.length() != 0){
			listName = n;
			
			return true;
		}else {
				return false;
			}
	}
	
	public String getName(){
		return listName;
	}
	
	public String[] getStringArray(){
		String[] a;
		a  = (String[]) this.toArray(new String[0]);
		System.out.println("a "+ a);
		return a;
	}
	public String getLastItem(){
		String n;
		n = (String) this.get(getSheetListSize()-1);
		return n;
	}
}
