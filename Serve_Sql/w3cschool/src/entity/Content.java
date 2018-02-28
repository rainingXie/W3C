package entity;

public class Content {
	private int id;
	private String title;
	private int parentId;
	private String contentText;
	private String codeShow;
	private int testTag;
	private String tryCode;
	private int label;

	public Content() {
		super();
	}

	public Content(int id, String title, int parentId, String contentText,
			String codeShow, int testTag, String tryCode) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.contentText = contentText;
		this.codeShow = codeShow;
		this.testTag = testTag;
		this.tryCode = tryCode;
	}

	public Content(int id, String title, int parentId, String contentText,
			String codeShow, int testTag, String tryCode, int label) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.contentText = contentText;
		this.codeShow = codeShow;
		this.testTag = testTag;
		this.tryCode = tryCode;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getCodeShow() {
		return codeShow;
	}

	public void setCodeShow(String codeShow) {
		this.codeShow = codeShow;
	}

	public int getTestTag() {
		return testTag;
	}

	public void setTestTag(int testTag) {
		this.testTag = testTag;
	}

	public String getTryCode() {
		return tryCode;
	}

	public void setTryCode(String tryCode) {
		this.tryCode = tryCode;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}
	
}
