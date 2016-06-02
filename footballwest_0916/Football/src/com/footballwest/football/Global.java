package com.footballwest.football;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

/**
 * Created by a on 8/23/15.
 */
public class Global {

	public static Context g_context;
    static public String[] leftListArray = { "Fixtures", "News", "Venues", "Business Director" };

    public static ArrayList<HashMap<String, String>> g_ladderData;
    
    public static boolean g_bLoading = false;
    
    public static int g_nDeep = 0;
    
    static public int NO = 0;
    public  Node fixtures;
    public Global(){
        getFixtures();
    };
    public class Node
    {
        public Node[] mNode;
        public String str;
        public String img;
        public int complist;
        public Node(Node[] mNode, String str, String img, int comp) {
            this.mNode = mNode;
            this.str = str;
            this.img = img;
            this.complist = comp;
        }

    }
    public void getFixtures() {
        Node node1 = new Node(null,"All Flags Division 1", null, 281330);
        Node node2 = new Node(null,"All Flags Division 1 Reserves", null, 281333);
        Node node3 = new Node(null,"All Flags Division 18s", null, 281335);
        Node[] temp1_3 = {node1, node2, node3};
        Node nodeParent1_3 = new Node(temp1_3,"Division 1 League","divone.png",0);

        Node node4 = new Node(null,"All Flags Division 2", null, 281331);
        Node node5 = new Node(null,"All Flags Division 2 Reserves", null, 281332);
        Node node6 = new Node(null,"All Flags Division 2 18s", null, 281334);
        Node[] temp4_6 = {node4, node5, node6};
        Node nodeParent4_6 = new Node(temp4_6,"Division 2 League","divtwo.png",0);

        Node[] tTemp1_6 = {nodeParent1_3,nodeParent4_6};
        fixtures = new Node(tTemp1_6,"State League","slp.png",0);
    }


}
