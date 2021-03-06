package ch.bolt61.minecraft.server.permissions;

import java.util.ArrayList;
import java.util.List;

import ch.bolt61.minecraft.server.Main;

public class Rank {

	private int id;
	private String name;
	private List<String> permissions;
	
	public Rank(int id, String name) {
		this.id = id;
		this.name = name;
		this.permissions = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void addPermission(String perm) {
		permissions.add(perm);
	}
	
	public boolean hasPermission(String permission) {
		return permissions.contains(permission);
	}
	
	public void updatePermission(String perm) {
		Main.getInstance().getMysql().query("SELECT * FROM permissions WHERE id='" + id + "' AND permission='" + perm + "'", rs -> {
			try {
				if(!rs.next()) {
					permissions.add(perm);
					Main.getInstance().getMysql().update("INSERT INTO permissions (id, permission) VALUES ('" + id + "', '" + perm + "'");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void removePermission(String perm) {
		Main.getInstance().getMysql().query("SELECT * FROM permissions WHERE id='" + id + "' AND permission='" + perm + "'", rs -> {
			try {
				if(rs.next()) {
					permissions.remove(perm);
					Main.getInstance().getMysql().update("DELETE * FROM permissions WHERE id='" + id + "' AND permission='" + perm + "'");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
