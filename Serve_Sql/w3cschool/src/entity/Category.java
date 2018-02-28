package entity;

public class Category {
	private int shceduleId;
	private String shceduleName;
	private int parentId;
	
	public int getShceduleId() {
		return shceduleId;
	}
	public void setShceduleId(int shceduleId) {
		this.shceduleId = shceduleId;
	}
	public String getShceduleName() {
		return shceduleName;
	}
	public void setShceduleName(String shceduleName) {
		this.shceduleName = shceduleName;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
}
