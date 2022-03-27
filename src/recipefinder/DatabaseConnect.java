package recipefinder;
import java.sql.*;
import static recipefinder.RecipeFinder.Ingredients;
import static recipefinder.MainUI.Userpreference;
/**
 * @author Bhavik Goplani
 * BTW I Changed some stuff :) - ADRIN 9AUG2019
 */

public class DatabaseConnect {
    
    // Variable Declaration
    static String[] AvailableRecipes;      // =new String[30];
    static String[] RecipeLinks;           //=new String[30];
    static String[] CommonIngredients;     //=new String[30];
    static String[] UncommonIngredients;   //=new String[30];
    static int arrn[];                     //=new int[30];//No. of common ingredients
    static int arrnn[];                    //=new int[30];//No. of uncommon ingredients
    //static int arr[]                      =new int[30];//No. of ingredients in each row
    static int recipeNo;
    static int no=0;//No of recipes in the database
    public static boolean CheckConnection(){
    
        Connection myconObj=null;
        Statement mystatObj=null;
        ResultSet myresObj=null;
        String query="SELECT * FROM RecipeDatabase";
        
        try{
            
          myconObj = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12293002", "sql12293002", "kWMW3DVsAK");
          mystatObj= myconObj.createStatement();          
          myresObj= mystatObj.executeQuery(query);
        }
          catch(SQLException e)
          {
            e.printStackTrace();
            return false;//When the connection is unsuccessful         
          }
        return true;//When the connection is successful         
    }
    
    public static void IngExtract (){
        
        /*Possible String UserPreference Values:
        None
        Vegeterian
        Non-Vegeterian
        Ayurvedic
        */  
       
        Connection myconObj=null;
        Statement mystatObj=null;
        Statement mystatObj1=null;
        ResultSet myresObj=null;
        ResultSet myresObj1=null;
        String query="SELECT * FROM RecipeDatabase";
        String query1="SELECT COUNT(*) FROM RecipeDatabase";//No of recipes in the database
        
        
        int ingreno=0;//Stores the no of individual ingredients
        try{
            
          myconObj = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12293002", "sql12293002", "kWMW3DVsAK");
          mystatObj= myconObj.createStatement();
          mystatObj1= myconObj.createStatement();
          myresObj= mystatObj.executeQuery(query);
          myresObj1= mystatObj1.executeQuery(query1);
                  
          String sen="",wd,stuff;
          char c,g;
          int a=0,b=0,d=0,e=0;
          
          while(myresObj1.next())
          {
               no=myresObj1.getInt(1);
          }
          System.out.println(no);
          
          AvailableRecipes    =new String[no];
          RecipeLinks         =new String[no];
          CommonIngredients   =new String[no];
          UncommonIngredients =new String[no];
          arrn                =new int[no];
          arrnn               =new int[no];
          
          
         while(myresObj.next()){
           for(int i=4;i<=23;i++)
           {
                if(Userpreference.equalsIgnoreCase(myresObj.getString(3)))
                {
                   stuff = myresObj.getString(i);
                //Extracting ingredients from the database
                if(stuff!=null){
                stuff.trim();
                sen = sen + stuff + "  ";
                }
            }
                if(Userpreference.equalsIgnoreCase("None"))
                {
                    stuff = myresObj.getString(i);
                //Extracting ingredients from the database
                if(stuff!=null){
                stuff.trim();
                sen = sen + stuff + "  ";
                }
                    
                }
           }  
          }
          int len= sen.length();
        for(int i=0;i<len;i++)
        { 
            c=sen.charAt(i);
            g=sen.charAt(i+1);
            if(c==' ' && g==' ')
            {
                a++;//Counts the no of ingredients
                i++;
            }
        }
        
        
        
        String arr[]= new String[a];//Stores all ingredients including duplicates
        String arrr[]= new String[a];//Stores individual ingredients
        int arrno[]=new int[a];//Counts the frequency of individual ingredients
        String f[]= new String[a];//Duplicated array for comparison
        for(int i=0;i<len;i++)
        { 
            if(sen.charAt(i)==' ' && sen.charAt(i+1)==' ')
            {
                wd=sen.substring(b,i);
                b=i+2;
                //System.out.println(i);
                arr[d]=wd;
                d++;
                i++;
                
        }
        }
        
        for(int i=0;i<a;i++)
        { f[i]=arr[i];
        }
        for(int i=0;i<a;i++)
        {
         for(int j=0;j<a;j++)
         {
             if(arr[i].equalsIgnoreCase(f[j])==true && f[j].equals(" ")==false)
             {
                 e++;//No of times the ingredient is repeated
                 f[j]=" ";
             }             
         }
            if(e>=1)
            {
                //System.out.println(arr[i]+"\t"+e);
                arrr[ingreno]=arr[i];
                arrno[ingreno]=e;
                ingreno++;            
            }
            e=0;
        }
                  
                  
            Ingredients = new String[ingreno];     
          for(int i=0; i<ingreno;i++)
          {
              Ingredients[i]=arrr[i];
          }    
        
          //Ingredientsort();
          for(int i=0;i<ingreno;i++)              
          System.out.println(Ingredients[i]+"   "+arrno[i]);
     
        }
          catch(SQLException e)
          {
               e.printStackTrace();
          } 
    }
    
    public static void extractRecipeDetails(String SelectedIngredients){
        
        System.out.println("Recieved Ings: "+SelectedIngredients);
        System.out.println("UserPreference: "+Userpreference);
        
        SelectedIngredients = SelectedIngredients.substring(1,SelectedIngredients.length()-1);
        SelectedIngredients = SelectedIngredients + ",";
        int a=0;//length of the user ingredients
        char c;
        for(int i=0;i<SelectedIngredients.length();i++)
        {
             c = SelectedIngredients.charAt(i);
            if (c==',')
                a++;
        }
        String SelectIngredients[] = new String[a];
        int h=0,m=0;
        for(int i=0; i<SelectedIngredients.length();i++)
        {
            if(SelectedIngredients.charAt(i)==',')
            {
                SelectIngredients[m]= SelectedIngredients.substring(h,i);
                m++;
                h=i+2;
            }
        }
        
        for(int i=0; i<a;i++)
        {
            System.out.println(SelectIngredients[i]);
        }
        
        Connection myconObj=null;
        Statement mystatObj=null;
        ResultSet myresObj=null;
        String query="SELECT * FROM RecipeDatabase";
        
        
        try
        {
            
          myconObj = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12293002", "sql12293002", "kWMW3DVsAK");
          mystatObj= myconObj.createStatement();
          myresObj= mystatObj.executeQuery(query);
          
          
          int n=0,n1=0,n2=0;
          
          arrn[n1]=0;
          //arrnn[n1]
          String arrr[]= new String[20];//To store the ingredients of the database
          String stuff;
          //myresObj= mystatObj.executeQuery(query);
          
          for (int i = 0; i< CommonIngredients.length; i++) {
              //Clears the Arrays
              CommonIngredients[i] = "";
              UncommonIngredients[i] = "";
          }
          
         while(myresObj.next()){
             
             for(int i=4;i<=23;i++)
             {
                 if(Userpreference.equalsIgnoreCase(myresObj.getString(3)))
                {
                   stuff = myresObj.getString(i);
                   //Extracting ingredients from the database
                   if(stuff!=null){
                    arrr[n]=stuff;
                    n++;
               }
                }
               if(Userpreference.equalsIgnoreCase("None"))
                {
                    stuff = myresObj.getString(i);
                    //Extracting ingredients from the database
                    if(stuff!=null){
                        arrr[n]=stuff;
                        n++;
                    }
                        
               }
             }
               
               if(Userpreference.equalsIgnoreCase(myresObj.getString(3)))
               {
                    AvailableRecipes[n1]= myresObj.getString(1);
                    AvailableRecipes[n1]=AvailableRecipes[n1].trim();
                    RecipeLinks[n1]= myresObj.getString(2);
                    RecipeLinks[n1]=RecipeLinks[n1].trim();
                    
                    
                for(int i=0; i<a; i++)//Compare
                 {
                    for(int k=0; k<n; k++)
                    {
                      if(SelectIngredients[i].equalsIgnoreCase(arrr[k]))
                      {
                          arrn[n1]++;
                          CommonIngredients[n1]= CommonIngredients[n1] + arrr[k] + ",";//Storing common ingredients
                           arrr[k]=null;
                      }
                      
                    }
                  
                  }
                
                
                for(int k=0;k<n;k++)
                {
                  if(arrr[k]!=null)
                  UncommonIngredients[n2] = UncommonIngredients[n2]+arrr[k]+",";//Storing uncommon ingredients
                }
                arrnn[n2]=n-arrn[n1];
                n1++;
                n2++;
                
                if(n1<no)
                {
                  arrn[n1]=0;
                }
                else
                    break;
                
             }
                
               if(Userpreference.equalsIgnoreCase("None")){
                   
                   AvailableRecipes[n1]= myresObj.getString(1);
                   AvailableRecipes[n1]=AvailableRecipes[n1].trim();
                   RecipeLinks[n1]= myresObj.getString(2);
                   RecipeLinks[n1]=RecipeLinks[n1].trim();
                    
                    
               for(int i=0; i<a; i++)//Compare
                {
                  for(int k=0; k<n; k++)
                  {
                      if(SelectIngredients[i].equalsIgnoreCase(arrr[k]))
                      {
                          arrn[n1]++;
                          CommonIngredients[n1]= CommonIngredients[n1] + arrr[k] + ",";//Storing common ingredients
                          arrr[k]=null;
                      }
                      

                  }
                  
                }
               
               for(int k=0;k<n;k++)
                {
                  if(arrr[k]!=null)
                  UncommonIngredients[n2] = UncommonIngredients[n2]+arrr[k]+",";//Storing uncommon ingredients
                }
               
                arrnn[n2]=n-arrn[n1];
                n1++;
                n2++;
              
                if(n1<no)
                {
                  arrn[n1]=0;
                }
               else
                    break;
               
             }
              n=0;     
                
            }
         
          //int count=0;
          for(int i=0; i< AvailableRecipes.length;i++)
          {
              if(CommonIngredients[i].length()<3 || CommonIngredients[i].isEmpty())//If there are no common Ingredients
              { 
                  //Clear the respective arrays
                  AvailableRecipes[i]=null;
                  RecipeLinks[i]=null;
                  CommonIngredients[i]=null;
                  UncommonIngredients[i]=null;
                  //continue;
              }
              /*              count++;
              System.out.println("Index " + i);
              System.out.println(AvailableRecipes[i]);
              System.out.println(RecipeLinks[i]);
              System.out.println(CommonIngredients[i]);
              System.out.println(UncommonIngredients[i]);
              System.out.println("_______________________________________________________________"+count);*/
              
          }
          ClearEmptySpaceInTheArrayAndSort();
          //SortArray();            
          //Refer the previous project to check what happens after this  
        
          
        }
        catch(SQLException e)
                {
                  e.printStackTrace();  
                } 
    }
    

    public static void Ingredientsort(){
        
        String temp;
        int n= Ingredients.length;
        
        for (int i = 0;  i<n; i++) 
        {
            for (int j = i + 1; j < n; j++) 
            {
                if (Ingredients[i].compareTo(Ingredients[j])>0) 
                {
                    temp = Ingredients[i];
                    Ingredients[i] = Ingredients[j];
                    Ingredients[j] = temp;
                }
            }
        }
    }
    
    private static void SortArray() {
        
        
        int num= 30;//No. of recipes
        //int recipeNo = 0;//:)
        //Uncommon Assortment
        for (int i = 0; i < num-1; i++)
        {
            for (int j = 0; j < num-i-1; j++)
            {
                if (arrnn[j] > arrnn[j+1])
                {
                    // swapping
                    int temp = arrnn[j];
                    arrnn[j] = arrnn[j+1];
                    arrnn[j+1] = temp;
                    String temp2 = AvailableRecipes[j];
                    AvailableRecipes[j] = AvailableRecipes[j+1];
                    AvailableRecipes[j+1]=temp2;
                    int temp3 = arrn[j+1];
                    arrn[j+1]=arrn[j];
                    arrn[j]=temp3;
                    String temp4= UncommonIngredients[j];
                    UncommonIngredients[j]= UncommonIngredients[j+1];
                    UncommonIngredients[j+1]= temp4;
                    String temp5= CommonIngredients[j+1];
                    CommonIngredients[j+1]= CommonIngredients[j];
                    CommonIngredients[j]= temp5;
                    String temp6= RecipeLinks[j];
                    RecipeLinks[j]= RecipeLinks[j+1];
                    RecipeLinks[j+1]= temp6;
                }
            }
        }
        //Common Assortment
         for (int i = 0; i < num-1; i++)
        {
            for (int j = 0; j < num-i-1; j++)
            {
                if (arrnn[j] == arrnn[j+1])
                {
                    if(arrn[j]<arrn[j+1])
                    {
                    int temp = arrnn[j];
                    arrnn[j] = arrnn[j+1];
                    arrnn[j+1] = temp;
                    String temp2 = AvailableRecipes[j];
                    AvailableRecipes[j] = AvailableRecipes[j+1];
                    AvailableRecipes[j+1]=temp2;
                    int temp3 = arrn[j+1];
                    arrn[j+1]=arrn[j];
                    arrn[j]=temp3;
                    String temp4= UncommonIngredients[j];
                    UncommonIngredients[j]= UncommonIngredients[j+1];
                    UncommonIngredients[j+1]= temp4;
                    String temp5= CommonIngredients[j+1];
                    CommonIngredients[j+1]= CommonIngredients[j];
                    CommonIngredients[j]= temp5;
                    String temp6= RecipeLinks[j];
                    RecipeLinks[j]= RecipeLinks[j+1];
                    RecipeLinks[j+1]= temp6;
                    }
                }
            }
        }
         
         //End of sort output:
         System.out.println("____after sort___________________________________________________________");
          System.out.println("_______________________________________________________________");
         for(int i=0; i< 20;i++)
          {
              if(CommonIngredients[i].length()==0)
              { 
                  continue;
              } 
              System.out.println("Index " + i);
              System.out.println(AvailableRecipes[i]);
              System.out.println(RecipeLinks[i]);
              System.out.println(CommonIngredients[i]);
              System.out.println(UncommonIngredients[i]);
              System.out.println("_______________________________________________________________");
              
          } 
    }
    
    private static void ClearEmptySpaceInTheArrayAndSort()     {
    boolean change=false;
    do
    {
        change=false;
        for(int i=0; i<no-2; i++)
        {
        if(AvailableRecipes[i]==null && AvailableRecipes[i+1]!=null)//If its empty & the next one isnt
        {
            AvailableRecipes[i]=AvailableRecipes[i+1];
            AvailableRecipes[i+1]=null;
            CommonIngredients[i]=CommonIngredients[i+1];
            CommonIngredients[i+1]=null;
            UncommonIngredients[i]=UncommonIngredients[i+1];
            UncommonIngredients[i+1]=null;
            RecipeLinks[i]=RecipeLinks[i+1];
            RecipeLinks[i+1]=null;
            change=true;
        }
    }
    }while(change);
    
    //SORTING
    for(int i=0; i<AvailableRecipes.length-1 ;i++)//Bubble Sort
    {
        if(AvailableRecipes[i+1]==null)//If its empty
            break;
        for(int j=0; j<AvailableRecipes.length-1-i; j++)
        {
            if(AvailableRecipes[j+1]==null)//If its empty
                break;
            
            if(wordCount(CommonIngredients[j]) < wordCount(CommonIngredients[j+1])//If there are more Com Ings
                    ||( wordCount(CommonIngredients[j])==wordCount(CommonIngredients[j+1]) //If the Com are equal
                        && wordCount(UncommonIngredients[j])>wordCount(UncommonIngredients[j+1])//Then If there are fewer Uncom
                        )
                )
            {
                //Switch All Values:
                String RecipeTemp = AvailableRecipes[j];
                String LinkTemp = RecipeLinks[j];
                String ComTemp = CommonIngredients[j];
                String UncomTemp = UncommonIngredients[j];
                
                AvailableRecipes[j] = AvailableRecipes[j+1];
                RecipeLinks[j] = RecipeLinks[j+1];
                CommonIngredients[j] = CommonIngredients[j+1];
                UncommonIngredients[j] = UncommonIngredients[j+1];
                
                AvailableRecipes[j+1]= RecipeTemp;
                RecipeLinks[j+1] = LinkTemp;
                CommonIngredients[j+1] = ComTemp;
                UncommonIngredients[j+1] = UncomTemp;  
            }
        }
    }
    
    //Confirming 
        for(int i=0;i<AvailableRecipes.length;i++)
        {
            System.out.println(AvailableRecipes[i]+
                    "\nCom: " + CommonIngredients[i]+
                    "\nUncom:"+UncommonIngredients[i]+
                    "\nLink: "+RecipeLinks[i]+"\n");
        }
    }
    
    private static int wordCount(String Ings)     {
    
        int count=0;
        for(int i=0; i< Ings.length(); i++)
        {
            if(Ings.charAt(i)==',')
                count++;
        }
    return count;
    }
}

