package com.damon.web;

@Re
public class ItemController {
    //post方式传递对象
    @GetMapping("/add2")
    public String addItem2(Item item){
        return itemConsumerService.addItem2(item);
    }
    //post方式传递对象
    @GetMapping("/add3")
    public String addItem3(Item item){
        return itemConsumerService.addItem3(item);
    }
}
