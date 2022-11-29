package io.github.usc_cs201_final_project.cs201_final_project_backend.packets;

public class ClientPlayAgainPacket extends PacketFormat {
	public boolean playAgain;

    public ClientPlayAgainPacket(boolean p)
    {
        playAgain = p;
    }

	public boolean isValidFormat() {
		return isFormatMatch(4);
	}
}
