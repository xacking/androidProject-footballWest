package com.footballwest.football;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {
	 
	public static DBManager g_dbManager = null;
	public static final String DATABASE_NAME = "footballwest.db";
	public static final int rootid = -1;
	
	
    public DBManager() {
        
    	super(Global.g_context, DATABASE_NAME, null, 1);
    }
 
    
    public static DBManager shareDBManager()
    {
    	if(g_dbManager == null)
    		g_dbManager = new DBManager();
    	
    	return g_dbManager;
    }
    @Override
    public void onCreate(SQLiteDatabase db) 
    {
    	db.execSQL("CREATE TABLE IF NOT EXISTS FIXTURES( ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, image TEXT, complist TEXT, superid INTEGER);");
    	db.execSQL("CREATE TABLE IF NOT EXISTS NEWS( ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, link TEXT, pubDate TEXT, description TEXT);");
    	db.execSQL("CREATE TABLE IF NOT EXISTS VENUES( ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, suburb TEXT, postcode TEXT, latitude TEXT, longitude TEXT, character TEXT);");
    	db.execSQL("CREATE TABLE IF NOT EXISTS BUSINESS( ID INTEGER PRIMARY KEY AUTOINCREMENT, bus_category_name TEXT, name TEXT, detail TEXT, address TEXT, city TEXT, state TEXT, country TEXT, postcode TEXT, phone TEXT, fax TEXT, email TEXT, web_url TEXT, google_rul TEXT, sponsor_text TEXT, moresponsorurl TEXT, sponsor_img_url TEXT);");
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
    	
    }
    //====================Fixtures========================================
    // save Fixtures data to db
    public void saveFixtures(ArrayList<HashMap<String, Object>> saveData)
    {
    	SQLiteDatabase db = getWritableDatabase();
    	db.delete("FIXTURES", null, null);
    	
    	for (HashMap<String, Object> element : saveData) 
    	{
    		
			String title = (String)element.get("title");									
			String image = (String)element.get("image");			
			String complist = (String)element.get("complist");			
			ArrayList<HashMap<String, Object>> children = (ArrayList<HashMap<String, Object>>)element.get("children");

			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.put("title", title);
			values.put("image", image);
			values.put("complist", complist);
			values.put("superid", rootid);

			// Insert the new row, returning the primary key value of the new row
			int superid = (int)db.insert("FIXTURES", null, values);
			
			if (children.size() > 0)
			{
				saveFixturesChildrenData(children, superid, db);
			}
			
		}
    }
    
    public void saveFixturesChildrenData(ArrayList<HashMap<String, Object>> data, int id, SQLiteDatabase db)
    {
    	for (HashMap<String, Object> element : data) 
    	{
    		
			String title = (String)element.get("title");									
			String image = (String)element.get("image");			
			String complist = (String)element.get("complist");			
			ArrayList<HashMap<String, Object>> children = (ArrayList<HashMap<String, Object>>)element.get("children");

			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.put("title", title);
			values.put("image", image);
			values.put("complist", complist);
			values.put("superid", id);

			// Insert the new row, returning the primary key value of the new row
			int superid = (int)db.insert("FIXTURES", null, values);
			
			if (children != null && children.size() > 0)
			{
				saveFixturesChildrenData(children, superid, db);
			}
			
		}
    }
    
    // get Fixtures data from db
    public ArrayList<HashMap<String, Object>> getFixtures()
    {
    	SQLiteDatabase db = getReadableDatabase();
    	ArrayList<HashMap<String, Object>> returnData = new ArrayList<HashMap<String, Object>>();
    	
    	String sql = String.format("select * from FIXTURES where superid=%d", rootid);
    	Cursor cursor = db.rawQuery(sql, null);
    	while(cursor.moveToNext()){
    		
    		HashMap<String, Object> row = new HashMap<String, Object>();
			
			String title = cursor.getString(1);						
			row.put("title", title);
			
			String image = cursor.getString(2);
			row.put("image", image);
			
			String complist = cursor.getString(3);
			row.put("complist", complist);
			
			int id = cursor.getInt(0);
			
			ArrayList<HashMap<String, Object>> children = getChildrens(id, db);
			if (children.size() > 0)
				row.put("children", children);

			returnData.add(row);
    	}
    	
    	return returnData;
    }
    
    public ArrayList<HashMap<String, Object>> getChildrens(int superid, SQLiteDatabase db)
    {
    	
    	ArrayList<HashMap<String, Object>> returnValue = new ArrayList<HashMap<String, Object>>();
    	
    	String sql = String.format("select *from FIXTURES where superid=%d", superid);
    	Cursor cursor = db.rawQuery(sql, null);
    	while(cursor.moveToNext()){
    		
    		HashMap<String, Object> row = new HashMap<String, Object>();
			
			String title = cursor.getString(1);						
			row.put("title", title);
			
			String image = cursor.getString(2);
			row.put("image", image);
			
			String complist = cursor.getString(3);
			row.put("complist", complist);
			
			int id = cursor.getInt(0);
			
			ArrayList<HashMap<String, Object>> children = getChildrens(id, db);
			if (children.size() > 0)
				row.put("children", children);

			returnValue.add(row);
    	}
    	
    	return returnValue;
    }
    
    
    //====================News====================
    // save News data to db
    public void saveNews(ArrayList<HashMap<String, String>> insertData)
    {
    	SQLiteDatabase db = getWritableDatabase();
    	
    	db.delete("News", null, null);
    	
    	for (HashMap<String, String> element : insertData) 
    	{
			String title = element.get("title");									
			String link = element.get("link");			
			String pubDate = element.get("pubDate");			
			String description = element.get("description");
		

			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.put("title", title);
			values.put("link", link);
			values.put("pubDate", pubDate);
			values.put("description", description);

			// Insert the new row, returning the primary key value of the new row
			db.insert("NEWS", null, values);
			
			
		}
    	
    }
    
    // get News data from db
    public ArrayList<HashMap<String, String>> getNews(){
    	
    	SQLiteDatabase db = getReadableDatabase();
    	
    	ArrayList<HashMap<String, String>> returnData = new ArrayList<HashMap<String, String>>();
    	
    	Cursor cursor = db.rawQuery("select *from NEWS", null);
    	while(cursor.moveToNext()){
    		
    		HashMap<String, String> row = new HashMap<String, String>();
			
			String title = cursor.getString(1);						
			row.put("title", title);
			
			String link = cursor.getString(2);
			row.put("link", link);
			
			String pubDate = cursor.getString(3);
			row.put("pubDate", pubDate);
			
			String description = cursor.getString(4);
			row.put("description", description);

			returnData.add(row);
    	}
    	return returnData;
    }
    
    //========================Venues=========================================
    // save Venues data to db
    public void saveVenues(ArrayList<HashMap<String, String>> insertData)
    {
    	SQLiteDatabase db = g_dbManager.getWritableDatabase();
    	db.delete("VENUES", null, null);
    	
    	for (HashMap<String, String> element : insertData) {
			String name = element.get("name");									
			String address = element.get("address");			
			String suburb = element.get("suburb");			
			String postcode = element.get("postcode");
			String latitude = element.get("latitude");
			String longitude = element.get("longitude");
			String character = element.get("character");
			
			
			ContentValues values = new ContentValues();
			values.put("name", name);
			values.put("address", address);
			values.put("suburb", suburb);
			values.put("postcode", postcode);
			values.put("latitude", latitude);
			values.put("longitude", longitude);
			values.put("character", character);

			// Insert the new row, returning the primary key value of the new row
			db.insert("VENUES", null, values);
						
		}
    }
    // get Venues data from db
    public ArrayList<HashMap<String, String>> getVenues(){
    	SQLiteDatabase db = g_dbManager.getReadableDatabase();

    	ArrayList<HashMap<String, String>> returnData = new ArrayList<HashMap<String, String>>();
    	
    	Cursor cursor = db.rawQuery("select * from VENUES", null);
    	while(cursor.moveToNext()){
    		
    		HashMap<String, String> row = new HashMap<String, String>();
			
			String name = cursor.getString(1);						
			row.put("name", name);
			
			String address = cursor.getString(2);
			row.put("address", address);
			
			String suburb = cursor.getString(3);
			row.put("suburb", suburb);
			
			String postcode = cursor.getString(4);
			row.put("postcode", postcode);
			
			String latitude = cursor.getString(5);						
			row.put("latitude", latitude);
			
			String longitude = cursor.getString(6);						
			row.put("longitude", longitude);
			
			String character = cursor.getString(7);
			row.put("character", character);
			
			returnData.add(row);
    	}
    	return returnData;
    }
    
    //===============business================================
    // save Business data to db
    public void saveBusiness(ArrayList<HashMap<String, String>> insertData)
    {
    	SQLiteDatabase db = g_dbManager.getWritableDatabase();
    	db.delete("BUSINESS", null, null);
    	
    	for (HashMap<String, String> element : insertData) 
    	{
    		// bus_category_name,name,detail,address,city,state,country,postcode,phone,fax,email,web_url,
			//google_url,sponsor_text,moresponsorurl,sponsor_img_url
    		
			String bus_category_name = element.get("bus_category_name");									
			String name = element.get("name");			
			String detail = element.get("detail");			
			String address = element.get("address");
			String city = element.get("city");									
			String state = element.get("state");			
			String country = element.get("country");			
			String postcode = element.get("postcode");
			String phone = element.get("phone");									
			String fax = element.get("fax");			
			String email = element.get("email");			
			String web_url = element.get("web_url");
			String google_rul = element.get("google_url");									
			String sponsor_text = element.get("sponsor_text");			
			String moresponsorurl = element.get("moresponsorurl");			
			String sponsor_img_url = element.get("sponsor_img_url");
			
			ContentValues values = new ContentValues();
			values.put("bus_category_name", bus_category_name);
			values.put("name", name);
			values.put("detail", detail);
			values.put("address", address);
			values.put("city", city);
			values.put("state", state);
			values.put("country", country);
			values.put("postcode", postcode);
			values.put("phone", phone);
			values.put("fax", fax);
			values.put("email", email);
			values.put("web_url", web_url);
			values.put("google_rul", google_rul);
			values.put("sponsor_text", sponsor_text);
			values.put("moresponsorurl", moresponsorurl);
			values.put("sponsor_img_url", sponsor_img_url);

			// Insert the new row, returning the primary key value of the new row
			db.insert("BUSINESS", null, values);
						
		}
    }
    // get Business data from db
    public ArrayList<HashMap<String, String>> getBusiness(){
    	SQLiteDatabase db = g_dbManager.getReadableDatabase();

    	ArrayList<HashMap<String, String>> returnData = new ArrayList<HashMap<String, String>>();
    	
    	Cursor cursor = db.rawQuery("select *from BUSINESS", null);
    	while(cursor.moveToNext()){
    		
    		HashMap<String, String> row = new HashMap<String, String>();
    		
    		String bus_category_name = cursor.getString(1);						
			row.put("bus_category_name", bus_category_name);
			
			String name = cursor.getString(2);						
			row.put("name", name);
			
			String detail = cursor.getString(3);
			row.put("detail", detail);
			
			String address = cursor.getString(4);
			row.put("address", address);
			
			String city = cursor.getString(5);
			row.put("city", city);
			
			String state = cursor.getString(6);						
			row.put("state", state);
			
			String country = cursor.getString(7);
			row.put("country", country);
			
			String postcode = cursor.getString(8);
			row.put("postcode", postcode);
			
			String phone = cursor.getString(9);
			row.put("phone", phone);
			
			String fax = cursor.getString(10);						
			row.put("fax", fax);
			
			String email = cursor.getString(11);
			row.put("email", email);
			
			String web_url = cursor.getString(12);
			row.put("web_url", web_url);
			
			String google_rul = cursor.getString(13);
			row.put("google_rul", google_rul);
			
			String sponsor_text = cursor.getString(14);						
			row.put("sponsor_text", sponsor_text);
			
			String moresponsorurl = cursor.getString(15);
			row.put("moresponsorurl", moresponsorurl);
			
			String sponsor_img_url = cursor.getString(16);
			row.put("sponsor_img_url", sponsor_img_url);

			returnData.add(row);
    	}
    	return returnData;
    }
    
   
}
