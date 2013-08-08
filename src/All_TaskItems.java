import java.io.Serializable;
import java.util.ArrayList;


public class All_TaskItems implements Serializable{
	private SheetList sheets_List;
	private ArrayList<ArrayList<TaskItem>> allList;
	
	public All_TaskItems(){
		sheets_List = new SheetList();
		allList = new ArrayList<ArrayList<TaskItem>>() ;
		
		
	}
	
	public SheetList get_sheetsList(){
		return sheets_List;
	}
	
	public ArrayList<ArrayList<TaskItem>> get_allList(){
		return allList;
	}

}
