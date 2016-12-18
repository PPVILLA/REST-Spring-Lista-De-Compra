package com.ppvilla.spring.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {
	private ConcurrentMap<Long, Item> items = new ConcurrentHashMap<>();
	private AtomicLong lastId = new AtomicLong();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Collection<Item> getAllItems() {
		return items.values();
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Item createItem(@RequestBody Item item) {
		long newId = lastId.incrementAndGet();
		item.setId(newId);
		items.put(newId, item);
		return item;

	}

	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<Item> getItem(@PathVariable long itemId) {
		Item item = items.get(itemId);
		if (item != null) {
			return new ResponseEntity<>(item, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{itemId}", method = RequestMethod.PUT)
	public ResponseEntity<Item> updateItem(@PathVariable long itemId, @RequestBody Item updateItem) {
		Item item = items.get(itemId);
		if (item != null) {
			updateItem.setId(itemId);
			items.put(itemId, updateItem);
			return new ResponseEntity<>(item, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Item> deleteItem(@PathVariable long itemId) {
		Item item = items.remove(itemId);
		if (item != null) {				
			return new ResponseEntity<>(item, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//http://server/items/?q=checked:true
	
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public Collection<Item> deleteItemChecked(@RequestParam String query) {
		String[] parts = query.split(":");
		//String field = parts[0];
		//String value = parts[1];
		boolean checked = Boolean.parseBoolean(parts[1]);
		List<Item>removedItem = new ArrayList<>();
		for(Item item : items.values()){
			if(item.isChecked()==checked){
				items.remove(item.getId());
			}
		}
		return removedItem;
	}

}
