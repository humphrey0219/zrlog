 package com.fzb.blog.controlle;
 
 import com.fzb.blog.model.Link;
 
 public class LinkControl extends ManageControl
 {
   public void delete()
   {
     Link.dao.deleteById(getPara("id"));
     renderJson("OK");
   }
 
   public void update() {
	   Link.dao.set("linkId", getPara("id")).set("linkName", getPara("linkName")).set("sort", getParaToInt("sort", 100)).set("url", getPara("url")).set("alt", getPara("alt")).update();
   }
 
   public void index() {
     render("/admin/link.jsp");
   }
 
   public void queryAll() {
     renderJson(Link.dao.queryAll(getParaToInt("page"),getParaToInt("rows")));
   }
   public void add(){
	   new Link().set("linkName", getPara("linkName")).set("sort", getParaToInt("sort", 100)).set("url", getPara("url")).set("alt", getPara("alt")).save();
   }
   
 }
