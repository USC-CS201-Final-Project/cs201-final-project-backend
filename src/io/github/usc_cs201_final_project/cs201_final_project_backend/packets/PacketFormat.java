package io.github.usc_cs201_final_project.cs201_final_project_backend.packets;

public abstract class PacketFormat {
	private int packetID;
	
	public abstract boolean isValidFormat();
	
	public boolean isFormatMatch(int i) {
		return packetID == i;
	}
}
