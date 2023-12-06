import com.spacehub.www.dao.SpaceDAO;

public class Test {
	public static void main(String[] args) {
		SpaceDAO dao = new SpaceDAO();
		int a = dao.getLastInsert();
		System.out.println(a);
	}
}
