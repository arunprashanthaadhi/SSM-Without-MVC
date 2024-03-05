public class Student {
	private int userId;
	private int rollNumber;
	private String name;
	private int english;
	private int cs;
	private int maths;
	private int physics;
	private int chemistry;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnglish() {
		return english;
	}

	public void setEnglish(int english) {
		this.english = english;
	}

	public int getCs() {
		return cs;
	}

	public void setCs(int cs) {
		this.cs = cs;
	}

	public int getMaths() {
		return maths;
	}

	public void setMaths(int maths) {
		this.maths = maths;
	}

	public int getPhysics() {
		return physics;
	}

	public void setPhysics(int physics) {
		this.physics = physics;
	}

	public int getChemistry() {
		return chemistry;
	}

	public void setChemistry(int chemistry) {
		this.chemistry = chemistry;
	}

	public String toString() {
		return "Student{" + "userId=" + userId + ", rollNumber=" + rollNumber + ", name='" + name + '\''
				+ ", english=" + english + ", cs=" + cs + ", maths=" + maths + ", physics=" + physics + ", chemistry="
				+ chemistry + '}';
	}
}
