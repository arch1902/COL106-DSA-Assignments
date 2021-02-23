import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;

public class A4_2019CS10332{

    static HashMap<String, LinkedList<Edge>> adj;
    static HashMap<String,Integer> cooc;
    static HashMap<String,Boolean> vis;
    static ArrayList<String> characters;
    static ArrayList<ArrayList<String>> components;
    static int num,cnt;

    public static <T> boolean isGreater(T X, T Y , int x)
    {
        if(X instanceof ArrayList) {
            ArrayList<String> a = (ArrayList)X;
            ArrayList<String> b = (ArrayList)Y;
            if(a.size()==b.size()) return rep(a.get(a.size()-1)).compareTo(rep(b.get(b.size()-1))) > 0;
            
            return a.size()>b.size();

        } else if(X instanceof String) {
            String a = (String)X;
            String b = (String)Y;
            if(x==1) return rep(a).compareTo(rep(b)) > 0 ;

            if(cooc.get(a).equals(cooc.get(b))){
                
                a=rep(a);
                b=rep(b);

                return a.compareTo(b) > 0 ;
            }     
            return cooc.get(a) > cooc.get(b);
        }
        return true;
    }

    public static String rep(String s){
        if(s.startsWith("\"") && s.endsWith("\"")) return s.substring(1,s.length()-1);
        return s;
    }


    public static void dfs(String s){
        vis.replace(s,true);
        components.get(cnt).add(s);
        for(Edge u : adj.get(s)){
            if(!vis.get(u.getId())) dfs(u.getId());
        }
    }


    public static void average(){
        if(num==0) return;
        float sum = 0;
        
        for(String s : adj.keySet()){
            sum += adj.get(s).size();
        }
        System.out.println(String.format("%.2f", sum/num));
    }

    public static void rank(){
        if(num==0) return;
        MergeSort(characters, 0, num-1,0);
        for(int i=num-1;i>0;i--){
            System.out.print(rep(characters.get(i))+",");
        }
        System.out.println(rep(characters.get(0)));

    }

    public static void independent_storylines_dfs(){
        if(num==0) return;
        components = new ArrayList<ArrayList<String>>();
        cnt=0;
        for(int i=0;i<num;i++){
            String s = characters.get(i);
            if(!vis.get(s)){
                components.add(new ArrayList<>());
                dfs(s);
                cnt++;
            }
        }

        for(int i=cnt-1;i>=0;i--){
            MergeSort(components.get(i),0,components.get(i).size()-1,1);
        }

        //System.out.println(cnt);
        MergeSort(components, 0, cnt-1, 1);
        for(int i=cnt-1;i>=0;i--){
            for(int j=components.get(i).size()-1;j>0;j--){
                System.out.print(rep(components.get(i).get(j))+",");
            }
            System.out.println(rep(components.get(i).get(0)));
        }
    }

      

    public static void main(String args[]) throws IOException {
        String nodes = args[0];
        String edges = args[1];
        String func = args[2];

        FileReader fr = new FileReader(nodes);
        adj = new HashMap<String,LinkedList<Edge>>();
        cooc = new HashMap<String,Integer>();
        vis = new HashMap<String,Boolean>();
        var br = new BufferedReader(fr);
        characters = new ArrayList<String>();
        String str = br.readLine();
        String[] line;
        while((str = br.readLine())!=null){

            line = str.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            adj.put(line[1],new LinkedList<Edge>());
            cooc.put(line[1],0);
            vis.put(line[1],false);
            characters.add(line[1]);
        }
        br.close();

        num=adj.size();

        fr = new FileReader(edges);
        br = new BufferedReader(fr);
        str = br.readLine();

        while((str = br.readLine())!=null){
            
            line = str.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            adj.get(line[0]).add(new Edge(line[1],Integer.parseInt(line[2])));
            adj.get(line[1]).add(new Edge(line[0],Integer.parseInt(line[2])));

            cooc.replace(line[0],cooc.get(line[0])+Integer.parseInt(line[2]));
            cooc.replace(line[1],cooc.get(line[1])+Integer.parseInt(line[2]));

        }
        br.close();

        switch (func) {
            case "average":
                average();
                break;
            case "rank":
                rank();
                break;
            case "independent_storylines_dfs":
                independent_storylines_dfs();
                break;
            default:
                break;
        }
    }



    public static <T> void Merge(ArrayList<T> arr , int l , int m ,int r ,int x){
        ArrayList<T> Left = new ArrayList<>();
        ArrayList<T> Right = new ArrayList<>();
        for(int i=0;i<m-l+1;i++){
            Left.add(arr.get(l+i));
        }
        for(int j=0;j<r-m;j++){
            Right.add(arr.get(m+1+j));
        }
        int k = l,i=0,j=0;
        while(i<m-l+1 && j<r-m){
            if(isGreater(Left.get(i),Right.get(j),x)) {
                arr.set(k,Right.get(j));
                j++;
            } else {
                arr.set(k,Left.get(i));
                i++;
            }
            k++; 
        }
        while(i<m-l+1){
            arr.set(k,Left.get(i));
            i++;k++;   
        }
        while(j<r-m){
            arr.set(k,Right.get(j));
            j++;k++;
        }

    }

    public static <T> void MergeSort(ArrayList<T> arr, int l , int r,int x){
        if(l<r){
            int mid =(l+r)/2;
            MergeSort(arr,l,mid,x);
            MergeSort(arr,mid+1, r,x);
            Merge(arr,l,mid,r,x);

        }
    }
}

class Edge{
    private String id;
    private int weight;
    public Edge(String id, int weight) {
        this.id = id;
        this.weight = weight;
    }
    public String getId() {
        return id;
    }
    public int getWeight() {
        return weight;
    }
}
