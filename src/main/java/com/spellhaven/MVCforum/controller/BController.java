package com.spellhaven.MVCforum.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spellhaven.MVCforum.command.BCommand;
import com.spellhaven.MVCforum.command.BContentCommand;
import com.spellhaven.MVCforum.command.BDeleteCommand;
import com.spellhaven.MVCforum.command.BListCommand;
import com.spellhaven.MVCforum.command.BModifyCommand;
import com.spellhaven.MVCforum.command.BReplyViewCommand;
import com.spellhaven.MVCforum.command.BWriteCommand;

@Controller
public class BController {
	
	BCommand command = null;
	
	// 어? 왜 이거 안 되고 home으로 가 버리지? 띠 용
	@RequestMapping("/") // 별 명령 없이 그냥 프로그램만 실행시켜도 list(글목록) 페이지로 가게 리디렉션해 주는 놈.
	public String root(Model model) {
		return "redirect:list";
	}
	
	// 글 목록 보여 주는 놈, list
	@RequestMapping("/list")
	public String list(Model model) {
		
		command = new BListCommand();
//		BListCommand command = new BListCommand(); 라고 쓸 수도 있지만, 코드 양을 줄이기 위해서
// 		L14에 쓰인 걸 봐라. 걔랑 연계해서 Bcommand로 만든 객체를 가져왔다. 업캐스팅. 이래도 된다.		
		command.execute(model);
		
		return "list";
	}
	
	// 글쓰기 창 열어 주기만 하는 놈, write_form
	@RequestMapping("/write_form")
	public String write_form() {
		return "write_form";
	}
	
	
	// 사용자가 쓴 글을 실제로 DB에 업로드해 주는 놈, write
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) { // 글 쓰는 함수니까, request도 있어야지. model에 request를 넣어서 전달하면 제일 간단하디.
		
		model.addAttribute("request", request);
		// 아니 선생님 request를 그냥 이렇게 통째로 실어도 돼요? 어. 이래도 돼. addAttribute는 "아무 object나 넣을 수 있으니까".
		
		command = new BWriteCommand();
		command.execute(model);
		
		return "redirect:list"; // 보통 글 작성 후에는 글 목록 창이 나오니까.
	}
	
	// 썼던 글 뭔 내용인지 보여 주는 놈, content_view
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		
		model.addAttribute("request", request);
		
		command = new BContentCommand();
		command.execute(model);
		
		return "content_view";
	}
	
	// 글 수정 창 보여 주는 놈, modify_view
	@RequestMapping("/modify_view")
	public String modify_view(HttpServletRequest request, Model model) {
		
		model.addAttribute("request", request);
		
		command = new BContentCommand(); // 굳이 BModifyViewCommand를 만들 필요 없다는 판단... 어차피 글 쓸 때랑 똑같은 화면으로 보여 주면 되잖아.
		command.execute(model);
		
		return "modify_view";
	}
	
	// 진짜로 수정해 주는 놈, modify
	@RequestMapping("/modify")
	public String modify(HttpServletRequest request, Model model) {
		
		model.addAttribute("request", request);
		
		command = new BModifyCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	
	// 마음에 안 드는 글 담그는 놈, delete
	@RequestMapping("/delete")
	// 어 얘도 request 있어야 하나?? 지금 생각: 어 있어야될걸? 글 하나에 대한 무슨 내용을 담고 있으니까. 있어야할듯.
	// 맞다. 교) bid를 request에 실어 다녀야 하니까.
	public String delete(HttpServletRequest request, Model model) {
		
		model.addAttribute("request", request);
		
		command = new BDeleteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		
		model.addAttribute("request", request);
		
		command = new BReplyViewCommand();
		command.execute(model);
		
		
		return "reply_view";
	}
	
}









