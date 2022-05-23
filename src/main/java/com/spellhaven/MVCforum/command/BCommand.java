package com.spellhaven.MVCforum.command;

import org.springframework.ui.Model;

public interface BCommand {

	void execute(Model model); //모델 세팅해 주는 놈.
	
}
