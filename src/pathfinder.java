//import jdk.internal.classfile.impl.BufWriterImpl;

//import java.sql.SQLOutput;
import java.util.*;

public class pathfinder {

    public static void addpath(List<Integer> path,List<road> roadlink,int cost){


        for(int i = 0; i<roadlink.size(); i++){

        }

       for(int i=0;i<path.size();i++){
         if(path.get(i) ==0)  System.out.print(a.n+":");
         if(path.get(i) ==1)  System.out.print(b.n+":");
         if(path.get(i) ==2)  System.out.print(c.n+":");
         if(path.get(i) ==3)  System.out.print(d.n+":");
         if(path.get(i) ==4)  System.out.print(e.n+":");
         if(path.get(i) ==5)  System.out.print(f.n+":");
         if(path.get(i) ==6)  System.out.print(g.n+":");
         if(path.get(i) ==7) { System.out.print(h.n+":");
         break;}
           if(roadlink.get(i).bus!=0&& !priorik) { System.out.print("--> (bus :"+roadlink.get(i).bus+")-->  ");
               cost+=roadlink.get(i).bus;}
           else if(roadlink.get(i).rik==0) { System.out.print("--> (bus :"+roadlink.get(i).bus+")-->  ");
               cost+=roadlink.get(i).bus;}

           else{
               System.out.print("-->(Rikshaw :"+roadlink.get(i).rik+")-->  ");
               cost+=roadlink.get(i).rik;
           }
       }
        System.out.println();
        System.out.println("Total cost : "+cost);
    }


    public static void printAllPaths(road[][] mat, road x, int src, int dest) {
        boolean[] visited = new boolean[mat.length];
        List<Integer> path = new ArrayList<>();
        List<road>roadlink=new ArrayList<>();
        dfsPaths(mat, x, src, dest, visited, path,roadlink);
    }

    private static void dfsPaths(road[][] mat, road x, int curr, int dest, boolean[] visited, List<Integer> path,List<road>roadlink) {
        visited[curr] = true;
        path.add(curr);

        if (curr == dest) {

          //  System.out.println(path);
            addpath(path,roadlink,0);// Print the current path
        } else {
            for (int i = 0; i < mat.length; i++) {
                if (mat[curr][i] != x && !visited[i]) {
                    roadlink.add(mat[curr][i]);
                    dfsPaths(mat, x, i, dest, visited, path,roadlink);
                }
            }
        }

        // Backtrack
       if(!roadlink.isEmpty()) {roadlink.removeLast();}

        path.removeLast();
        visited[curr] = false;
    }




    public static void printway(road[][] mat,road x){
        for(int i=0;i<mat.length;i++) {
            for(int j=0;j<mat[i].length;j++) {
                if(!mat[i][j].equals(x)) {
                    System.out.print(i+"--"+j);
                    System.out.print("||");
                }

            }
            System.out.println();
        }
    }

    public static void bfst(road[][] mat,road x){
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(0);
        for(int i=0;i<mat.length;i++) {
            for(int j=0;j<mat[i].length;j++) {
                if(!mat[i][j].equals(x)&&!q.contains(j)) {
                    q.add(j);
                }
            }
        }
        System.out.println(q);

    }

    static area a=new area("Mirpur 14 ");
    static area b=new area("Banani");
    static area c=new area("ECB ");

    static  area d=new area("jahangirgate");
    static  area e=new area("Gulshan");
    static  area f=new area("Jamuna");
    static  area g=new area("Notunbazar");
    static  area h=new area("BRAC");

    static boolean priorik=false;



    public static void main(String[] args) {

Scanner sc=new Scanner(System.in);


 road x=new road (null,null,0,0);
        road[][] mat = {
                {x, new road(a,b,10,80), new road(a,c,20,60), new road(a,d,0,80), x, x, x, x},
                {x, x, x, x, new road(b,e,10,0), x, x, x},
                {x, x, x, x, x, new road(c,f,10,0), x, x},
                {x, x, x, x, x, x, x, new road(d,h,0,120)},
                {x, x, x, x, x, x, new road(e,g,10,0), x},
                {x, x, x, x, x, x, new road(f,g,10,0), x},
                {x, x, x, x, x, x, x, new road(g,h,10,60)},
                {x, x, x, x, x, x, x, x},
        };

        System.out.println("From where you will start your journey ?");
        System.out.println(  "Index\tArea\n" +
                "0\t     Mirpur 14\n" +
                "1\t     Banani\n" +
                "2\t     ECB\n" +
                "3\t     Jahangirgate\n" +
                "4\t     Gulshan\n" +
                "5\t     Jamuna\n" +
                "6\t     Notunbazar\n" );
        System.out.println("Enter index no :");
        int n=sc.nextInt();
        System.out.println("Whats your vehicle preference ?\n" +
                "Rikshaw       0\n"
           +"Bus           1" );
        System.out.println("\n"+"Enter preference :");
        int p=sc.nextInt();

        if(p==0) {priorik=true;}



        System.out.println("All connection :");
        printway(mat,x);

     // bfst(mat,x);

      boolean []visited= new boolean[8];

      //dfs(mat,visited,0,x);
      //  System.out.println();
        System.out.println("Your possible routes : ");
     printAllPaths(mat,x,n,7);

    }

    public static void dfs(road[][] mat, boolean[] visited, int current, road x) {
        visited[current] = true;
        System.out.print(current + " ");

        for (int i = 0; i < 8; i++) {
            if (!visited[i] && !mat[current][i].equals(x)) {
                dfs(mat, visited, i, x);
            }
        }
    }


}






 class road {


    area src;
    area dest;
    int bus;
    int rik;
    road(area src, area dest, int bus, int rik) {
        this.src = src;
        this.bus = bus;
        this.rik = rik;
        this.dest = dest;

    }
}




 class area {
    String n;
    // int bus;
    //  int rik;
    area(String n) {
        this.n = n;
        //   this.bus = bus;
        //  this.rik = rik;

    }
}

