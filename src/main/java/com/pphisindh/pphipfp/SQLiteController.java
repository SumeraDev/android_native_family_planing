package com.pphisindh.pphipfp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.pphisindh.pphipfp.Beans.AttendanceBean;
import com.pphisindh.pphipfp.Beans.DistrictBean;
import com.pphisindh.pphipfp.Beans.Employee;
import com.pphisindh.pphipfp.Beans.OptionBean;
import com.pphisindh.pphipfp.Beans.Questions_category;
import com.pphisindh.pphipfp.Beans.Questionsbean;
import com.pphisindh.pphipfp.Beans.Tbl_emp_serving;
import com.pphisindh.pphipfp.Beans.Tbl_hf_services_avail;
import com.pphisindh.pphipfp.Beans.Tbl_hf_servicesnew;
import com.pphisindh.pphipfp.Beans.VisitDetailBean;
import com.pphisindh.pphipfp.Beans.VisitMasterBean;
import com.pphisindh.pphipfp.Tables.Employees;
import com.pphisindh.pphipfp.Tables.HealthFacilities;
import com.pphisindh.pphipfp.Tables.Option;
import com.pphisindh.pphipfp.Tables.Questions;
import com.pphisindh.pphipfp.Tables.QuestionsOptions;
import com.pphisindh.pphipfp.Tables.Visit_Detail;
import com.pphisindh.pphipfp.Tables.Visit_master;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 12/8/2016.
 */

public class SQLiteController extends SQLiteOpenHelper {





    public static String  tbl_Employees="Employees";
    public static String  tbl_HealthFacilities="HealthFacility";
    public static String  tbl_questions="questions";
    public static String  tbl_Options="options";
    public static String  tbl_QuestionsOpt="ques_option";
    public static String  tbl_Visit_master="visit_master";
    public static String  tbl_visit_detail="visit_detail";

    public SQLiteController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE tbl_Login(EMP_ID INTEGER,CNIC TEXT,PWD TEXT,PINCODE TEXT)");
        db.execSQL("CREATE TABLE "+tbl_Employees+" ( "+ Employees.e_id+"  "+ Employees.e_idType+" ,"
                + Employees.e_EMP_ID+" "+ Employees.e_EMP_IDType+" , "
                + Employees.e_EMP_NAME+" "+ Employees.e_EMP_NAMEType+" , "
                + Employees.e_MOBILE+" "+ Employees.e_MOBILEType+" , "
                + Employees.e_cnic+" "+ Employees.e_FK_HF_IDType+" , "
                + Employees.e_FK_HF_ID+" "+ Employees.e_PARENT_HF_IDType+" , "
                + Employees.e_PARENT_HF_ID+" "+ Employees.e_PARENT_HF_IDType+" , "
                + Employees.e_EMP_DESIG_ID+" "+ Employees.e_EMP_DESIG_IDType+" , "
                + Employees.e_EMP_CODE+" "+ Employees.e_EMP_CODEType+" ,   "
                + Employees.e_emp_desig+" "+ Employees.e_emp_desigType+" )");

        db.execSQL("CREATE TABLE "+tbl_HealthFacilities+" ( "+ HealthFacilities.h_id+"  "+ HealthFacilities.h_idType+" ,"
                + HealthFacilities.h_HF_ID+" "+ HealthFacilities.h_HF_IDType+" , "
                + HealthFacilities.h_HF_DISP_NAME+" "+ HealthFacilities.e_HF_DISP_NAMEType+" , "
                + HealthFacilities.h_HF_CONTACT_NUM+" "+ HealthFacilities.e_HF_CONTACT_NUMType+" , "
                + HealthFacilities.h_HF_OFFICE_LEVEL+" "+ HealthFacilities.e_HF_OFFICE_LEVELType+" , "
                + HealthFacilities.h_HF_PARENT_OFFICE+" "+ HealthFacilities.e_HF_PARENT_OFFICEType+" , "
                + HealthFacilities.h_distType+" "+ HealthFacilities.e_distTypeType+" )");

        db.execSQL("CREATE TABLE "+tbl_questions+" ( "+ Questions.q_id+"  "+ Questions.q_idType+" ,"
                + Questions.q_question+" "+ Questions.q_questionType+" , "
                + Questions.q_is_active+" "+ Questions.q_is_activeType+" , "
                + Questions.q_is_hr+" "+ Questions.q_is_hr+" , "
                + Questions.q_mo_fmo_both+" "+ Questions.q_mo_fmo_bothType+",ques_catId TEXT,ordBy TEXT)");

        db.execSQL("CREATE TABLE "+tbl_Options+" ( "+ Option.o_id+"  "+ Option.o_idType+" ,"
                + Option.o_option_name+" "+ Option.o_option_nameType+" , "
                + Option.o_mark+" "+ Option.o_markType+" , "
                + Option.o_is_active+" "+ Option.o_is_activeType+" )");

        db.execSQL("CREATE TABLE "+tbl_QuestionsOpt+" ( "+ QuestionsOptions.qo_id+"  "+ QuestionsOptions.qo_idType+" ,"
                + QuestionsOptions.qo_qId+"  "+ QuestionsOptions.qo_qIdType+" ,"
                + QuestionsOptions.qo_oId+"  "+ QuestionsOptions.qo_oIdType+" ,"
                + QuestionsOptions.qo_description+"  "+ QuestionsOptions.descriptionType+")");

        db.execSQL("CREATE TABLE "+tbl_Visit_master+" ( "+ Visit_master.vm_id+"  "+ Visit_master.vm_idType+" ,"
                + Visit_master.vm_code+"  "+ Visit_master.vm_codeType+" ,"
                + Visit_master.vm_hfid+"  "+ Visit_master.vm_hfidType+" ,"
                + Visit_master.vm_phfid+"  "+ Visit_master.vm_phfidType+" ,"
                + Visit_master.vm_emp_id+"  "+ Visit_master.vm_emp_idType+" ,"
                + Visit_master.vm_start_time+"  "+ Visit_master.vm_start_timeType+" ,"
                + Visit_master.vm_end_time+"  "+ Visit_master.vm_end_timeType+" ,"
                + Visit_master.vm_is_active+"  "+ Visit_master.vm_is_activeType+" ,"
                + Visit_master.vm_latitude+"  "+ Visit_master.vm_latitudeType+" ,"
                + Visit_master.vm_longitude+"  "+ Visit_master.vm_longitudeType+" ,"
                + Visit_master.vm_imgsource+"  "+ Visit_master.vm_imgsourceType+" ,"
                + Visit_master.vm_mo_empcode+"  "+ Visit_master.vm_mo_empcodeType+" ,"
                + Visit_master.vm_fmo_empcode+"  "+ Visit_master.vm_fmo_empcodeType+",qtr_id Text )");

        db.execSQL("CREATE TABLE "+tbl_visit_detail+" ( "+ Visit_Detail.vd_id+"  "+ Visit_Detail.vd_idType+" ,"
                + Visit_Detail.vd_vm_id+"  "+ Visit_Detail.vd_vm_idType+" ,"
                + Visit_Detail.vd_vm_code+"  "+ Visit_Detail.vd_vm_codeType+" ,"
                + Visit_Detail.vd_qid+"  "+ Visit_Detail.vd_qidType+" ,"
                + Visit_Detail.vd_oid+"  "+ Visit_Detail.vd_oidType+" ,"
                + Visit_Detail.vd_is_active+"  "+ Visit_Detail.vd_is_activeType+" )");

        db.execSQL("CREATE TABLE attendance(id INTEGER PRIMARY KEY AUTOINCREMENT,vm_code TEXT,qId INTEGER,oId INTEGER,lateDescriptin TEXT,isempCode INTEGER,emp_code TEXT,is_active INTEGER,isPresent INTEGER);");

        db.execSQL("CREATE TABLE questions_category(id INTEGER PRIMARY KEY,ques_cat TEXT,is_active TEXT,applyOn TEXT,ordBy TEXT )");
        db.execSQL("CREATE TABLE tbl_hf_servicesnew(id INTEGER PRIMARY KEY ,servicesName TEXT,is_active TEXT)");
        db.execSQL("CREATE TABLE tbl_hf_services_avail(id INTEGER PRIMARY KEY AUTOINCREMENT,`serviceType_id` TEXT,`fk_hfid` TEXT,`hfid` TEXT,`qtr_id` TEXT,is_active Text)");
        db.execSQL("CREATE TABLE tbl_emp_serving(id INTEGER PRIMARY KEY AUTOINCREMENT,ques_catId TEXT,fk_hfid TEXT,hfid TEXT,qtr_id TEXT,visit_masterId TEXT,emp_code TEXT,empname TEXT,is_active TEXT)");
        db.execSQL("CREATE TABLE p4p_quarter(id INTEGER PRIMARY KEY ,qtr TEXT,is_running TEXT)");


    }

    public String updateEmployees(String emp_code,String vcode){
        String msg="notUpdated";
        try{
            String qMasterVisitDet="UPDATE  tbl_emp_serving SET is_active=1,visit_masterId='"+vcode+"' WHERE emp_code='"+emp_code+"'";
            SQLiteDatabase db = this.getWritableDatabase();
            Log.i("sql1=", qMasterVisitDet);
            db.execSQL(qMasterVisitDet);
            db.close();
            msg="Updated";

        }catch (Exception ex){
            msg="notUpdated";
        }

        return msg;
    }


    public ArrayList<Employee> getEmployees(String fkhfid, String hfid, String qtrid, Activity activity){
       ArrayList<Employee> emplist=new ArrayList<>();
       try{
               String query="Select e.`fk_hfid`,e.`hfid`,e.`empname`,e.`emp_code`,e.`qtr_id` \n" +
                       " from `tbl_emp_serving` e where e.is_active=2 AND e.`fk_hfid`="+fkhfid+"  and e.`hfid`="+hfid+" and e.`qtr_id`=" +qtrid
                       +"  group by e.`emp_code`";
               Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
               while (cursor.moveToNext()){
                   //Employee( int FK_HF_ID, int PARENT_HF_ID,String EMP_NAME, String EMP_CODE)
                   emplist.add(new Employee(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3)));
               }
               cursor.close();

           }catch (Exception ex){
                Toast.makeText(activity,"Exception:"+ex.getMessage(),Toast.LENGTH_LONG).show();
           }
           return emplist;

   }

    public  ArrayList<Tbl_hf_servicesnew> getServices(){
        ArrayList<Tbl_hf_servicesnew> list=new ArrayList<>();
        try{
            try{
                String query="SELECT id ,servicesName ,'0' FROM tbl_hf_servicesnew";
                Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
                while (cursor.moveToNext()){
                    list.add(new Tbl_hf_servicesnew(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
                }
                cursor.close();

            }catch (Exception ex){

            }

        }catch (Exception ex){

        }

        return list;
    }
    public  ArrayList<Questions_category> getQuestioncat(){
        ArrayList<Questions_category> list=new ArrayList<>();
           try{
                String query="SELECT id   ,ques_cat ,'0' ,applyOn  FROM questions_category ";
                Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
                while (cursor.moveToNext()){
                    list.add(new Questions_category(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
                }
                cursor.close();

            }catch (Exception ex){

            }


        return list;
    }
    public int getQTRID(){
        int qid=0;
        try{
            try{
                String query="SELECT id  FROM p4p_quarter ";
                Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
                while (cursor.moveToNext()){
                    qid=cursor.getInt(0);
                }
                cursor.close();

            }catch (Exception ex){

            }

        }catch (Exception ex){

        }
        return qid;
    }

    public String insertempserving(ArrayList<Tbl_emp_serving> emp_servings){
        String msg="notInserted";
        try{
            String queryInsert= "";

//            for(Tbl_emp_serving emp_serving:emp_servings) {
//                queryInsert = " DELETE FROM `tbl_emp_serving` WHERE `fk_hfid`=" + emp_serving.getFk_hfid() + " AND `hfid`=" + emp_serving.getHfid() + " AND `qtr_id`=" + emp_serving.getQtr_id() + " AND `emp_code`='" + emp_serving.getEmp_code() + "'; ";
//                SQLiteDatabase db = this.getWritableDatabase();
//                Log.i("sql1=",queryInsert);
//                db.execSQL(queryInsert);
//            }
            for(Tbl_emp_serving emp_serving:emp_servings){
                queryInsert="INSERT INTO tbl_emp_serving(ques_catId,fk_hfid,hfid,qtr_id,visit_masterId,emp_code,empname,is_active)" +
                        " VALUES("+emp_serving.getQues_catId()+","+emp_serving.getFk_hfid()+","+emp_serving.getHfid()+","+emp_serving.getQtr_id()
                        +",'0','"+emp_serving.getEmp_code()+"','"+emp_serving.getEmpname()+"',2)";
                SQLiteDatabase db = this.getWritableDatabase();
                Log.i("sql1=",queryInsert);
                db.execSQL(queryInsert);
                msg="Inserted";
            }

        }catch (Exception ex){
            msg="Exception:"+ex.getMessage();
        }
        return msg;
    }

    public String inserthfservices(ArrayList<String> services,String fkhhfid,String hfid){
        String msg="notInserted";
        try{
            for(String service:services){

              SQLiteDatabase db = this.getWritableDatabase();
//                String queryInsert=" DELETE FROM tbl_hf_services_avail  WHERE qtr_id="+getQTRID()+" AND hfid="+hfid+" AND `fk_hfid`="+fkhhfid+"; ";
//                db.execSQL(queryInsert);

                String queryInsert="";
                db = this.getWritableDatabase();
                queryInsert= "INSERT INTO tbl_hf_services_avail(`serviceType_id` ,`fk_hfid` ,`hfid` ,`qtr_id` ,is_active )" +
                        " VALUES("+service+","+fkhhfid+","+hfid+","+getQTRID()+",'2')";

                Log.i("sql1=",queryInsert);
                db.execSQL(queryInsert);
                msg="Inserted";
            }

        }catch (Exception ex){
            msg="Exception:"+ex.getMessage();
        }
        return msg;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_Login");
        db.execSQL("DROP TABLE IF EXISTS "+tbl_Employees);
        db.execSQL("DROP TABLE IF EXISTS "+tbl_questions);
        db.execSQL("DROP TABLE IF EXISTS "+tbl_Options);
        db.execSQL("DROP TABLE IF EXISTS "+tbl_QuestionsOpt);
        db.execSQL("DROP TABLE IF EXISTS "+tbl_Visit_master);
        db.execSQL("DROP TABLE IF EXISTS "+tbl_visit_detail);
        db.execSQL("DROP TABLE IF EXISTS attendance");
        onCreate(db);
    }
    public void Reset(){
        try{


            SQLiteDatabase dbdel=this.getWritableDatabase();
            int cursor = dbdel.delete("tbl_Login", "1", null);
            dbdel.close();

            dbdel=this.getWritableDatabase();
            cursor = dbdel.delete("tbl_Log", "1", null);
            dbdel.close();
            //--------------------------
            /*dbdel=this.getWritableDatabase();
            cursor = dbdel.delete("tbl_district", "1", null);
            dbdel.close();
            //--------------------------
            dbdel=this.getWritableDatabase();
            cursor = dbdel.delete("tbl_subdiv", "1", null);
            dbdel.close();
            //----------------------------

            dbdel=this.getWritableDatabase();
            cursor = dbdel.delete("tbl_unioncon", "1", null);
            dbdel.close();
            //----------------------------
            dbdel=this.getWritableDatabase();
            cursor = dbdel.delete("epi_village_master", "1", null);
            dbdel.close();*/
            //----------------------------
            dbdel=this.getWritableDatabase();
            cursor = dbdel.delete("tbl_Vacinators", "1", null);
            dbdel.close();

            //--------------------------
            /*dbdel=this.getWritableDatabase();
            cursor = dbdel.delete("tbl_healthfacility", "1", null);
            dbdel.close();
            //----------------------------
            dbdel=this.getWritableDatabase();
            cursor = dbdel.delete("epi_Vaccine", "1", null);
            dbdel.close();

            //----------------------------
            dbdel=this.getWritableDatabase();
            cursor = dbdel.delete("epi_schedule", "1", null);
            dbdel.close();*/

            //----------------------------
            dbdel=this.getWritableDatabase();
            cursor = dbdel.delete("tbl_children", "1", null);
            dbdel.close();

            //----------------------------
            dbdel=this.getWritableDatabase();
            cursor = dbdel.delete("child_vaccinations", "1", null);
            dbdel.close();



            //--------------------------
        }catch (Exception ex){

        }
    }


    public int chkLogin(){
        int chklogin=0;
            try{
                SQLiteDatabase db = this.getWritableDatabase();
                String qLogin = "SELECT * FROM tbl_Login";
                Log.i("sql1=",qLogin);
                Cursor cursor = this.getReadableDatabase().rawQuery(qLogin,null);
                    chklogin=cursor.getCount();
                cursor.close();

            }catch (Exception ex){
                chklogin=0;
            }
        return chklogin;
    }
    public String resetLogin(String cnic,String pincode){
        String msg="Invalid Cnic or Pincode...";
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String sql1 = "UPDATE tbl_Login SET pincode='"+pincode+"' WHERE  cnic='"+cnic+"'";
            Log.i("sql1=",sql1);
            db.execSQL(sql1);

            msg="Password has been Reset....";

        }catch (Exception ex){
            msg="Error to Reset Password : "+ex.getMessage();
        }
        return msg;
    }

    public String InsertLogin(int EMP_ID, String CNIC,String pwd,String pincode){
        String msg="Login has not been Inserted";
        try{
                String queryInsert="INSERT INTO tbl_Login(EMP_ID,CNIC,PWD,PINCODE) VALUES("+EMP_ID+",'"+CNIC+"','"+pwd+"','"+pincode+"')";
                SQLiteDatabase db = this.getWritableDatabase();
                Log.i("sql1=",queryInsert);
                db.execSQL(queryInsert);
                msg="Login Inserted";

            //
        }catch (Exception ex){
            msg="Error in Login:"+ex.getMessage();
        }
        return msg;
    }

    public int verifyPincode(String pincode){
        int isVerified=0;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String qLogin = "SELECT * FROM tbl_Login WHERE  PINCODE='"+pincode+"'";
            Log.i("sql1=",qLogin);
            Cursor cursor = this.getReadableDatabase().rawQuery(qLogin,null);
            isVerified=cursor.getCount();
            cursor.close();

        }catch (Exception ex){

        }
        return isVerified;
    }



    public String downloadMasterVisit(JSONArray jsonArray){
        String msg="NotInserted";
        try{
            if(countMasterVisits()==0) {

                for (int index = 0; index < jsonArray.length(); index++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    String code=jsonObject.getString("code");
                    int hfid= jsonObject.getInt("hfid");
                    int phfid= jsonObject.getInt("phfid");
                    int emp_id= jsonObject.getInt("emp_id");
                    String start_time=jsonObject.getString("start_time");
                    String end_time=jsonObject.getString("end_time");
                    int is_active= 0;
                    String longitude=jsonObject.getString("longitude");
                    String latitude=jsonObject.getString("latitude");
                    String imgsource=jsonObject.getString("imgsource");
                    String mo_empcode=jsonObject.getString("mo_empcode");
                    String fmo_empcode=jsonObject.getString("fmo_empcode");

                    String qMasterVisit="INSERT INTO visit_master(code,hfid,phfid,emp_id,start_time,end_time,is_active,longitude,latitude,imgsource,mo_empcode,fmo_empcode) ";
                    qMasterVisit=qMasterVisit+" VALUES('"+code+"',"+hfid+","+phfid+","+emp_id+",'"+start_time+"','"+end_time+"',"+is_active+",'"+longitude+"','"+latitude+"','"+imgsource+"','"+mo_empcode+"','"+fmo_empcode+"')";

                    SQLiteDatabase db = this.getWritableDatabase();
                    Log.i("sql1=", qMasterVisit);
                    db.execSQL(qMasterVisit);
                    msg="Inserted";
                    db.close();

                }
            }

        }catch (Exception ex){
            msg="Error in visit_master:"+ex.getMessage();
        }
        return msg;
    }

    public String downloadHealthFacility(JSONArray jsonArray){
        String msg="NotInserted";
        try{
            SQLiteDatabase dbdel=this.getWritableDatabase();
            int cursor = dbdel.delete("HealthFacility", "1", null);
            dbdel.close();

            for(int index=0;    index<jsonArray.length();   index++){
                JSONObject jsonObject=jsonArray.getJSONObject(index);
                int HF_ID=jsonObject.getInt("HF_ID");
                String HF_DISP_NAME=jsonObject.getString("HF_DISP_NAME");
                String HF_CONTACT_NUM=jsonObject.getString("HF_CONTACT_NUM");
                int HF_OFFICE_LEVEL=jsonObject.getInt("HF_OFFICE_LEVEL");

                int HF_PARENT_OFFICE=jsonObject.getInt("HF_PARENT_OFFICE");
                int distType=jsonObject.getInt("distType");

                String queryInsertHealthFacility="INSERT INTO HealthFacility (HF_ID,HF_DISP_NAME,HF_CONTACT_NUM,HF_OFFICE_LEVEL,HF_PARENT_OFFICE,distType) ";
                queryInsertHealthFacility=queryInsertHealthFacility+" VALUES("+HF_ID+",'"+HF_DISP_NAME+"','"+HF_CONTACT_NUM+"',"+HF_OFFICE_LEVEL+","+HF_PARENT_OFFICE+","+distType+")";
                SQLiteDatabase db = this.getWritableDatabase();
                Log.i("sql1=",queryInsertHealthFacility);
                db.execSQL(queryInsertHealthFacility);
                msg="Inserted";
                db.close();

            }

        }catch (Exception ex){
            msg="Error in HealthFacility:"+ex.getMessage();
        }
        return msg;
    }

    public String downloadEmployees(JSONArray jsonArray){
        String msg="NotInserted"+jsonArray.length();
        try{
            SQLiteDatabase dbdel=this.getWritableDatabase();
            int cursor = dbdel.delete("Employees", "1", null);
            dbdel.close();

            for(int index=0;    index<jsonArray.length();   index++){
                JSONObject jsonObject=jsonArray.getJSONObject(index);
                int EMP_ID=jsonObject.getInt("EMP_ID");
                String EMP_NAME=jsonObject.getString("EMP_NAME");
                String MOBILE=jsonObject.getString("MOBILE");
                String CNIC=jsonObject.getString("CNIC");
                int FK_HF_ID=jsonObject.getInt("FK_HF_ID");
                int PARENT_HF_ID=jsonObject.getInt("PARENT_HF_ID");
                int DESIGNATION_ID=jsonObject.getInt("DESIGNATION_ID");
                String EMP_CODE=jsonObject.getString("EMP_CODE");
                String emp_desig=jsonObject.getString("emp_desig");

                String queryInsertEmployee="INSERT INTO Employees (EMP_ID,EMP_NAME,MOBILE,CNIC,FK_HF_ID,PARENT_HF_ID,DESIGNATION_ID,EMP_CODE,emp_desig) ";
                queryInsertEmployee=queryInsertEmployee+" VALUES("+EMP_ID+",'"+EMP_NAME+"','"+MOBILE+"','"+CNIC+"',"+FK_HF_ID+","+PARENT_HF_ID+","+DESIGNATION_ID+",'"+EMP_CODE+"','"+emp_desig+"')";
                SQLiteDatabase db = this.getWritableDatabase();
                Log.i("sql1=",queryInsertEmployee);
                db.execSQL(queryInsertEmployee);
                msg="Inserted";
                db.close();

            }

        }catch (Exception ex){
            msg="Error in Employees:"+ex.getMessage();
        }
        return msg;
    }

    public String downloadquestions(JSONArray jsonArray,Activity activity){
        String msg="NotInserted";
        try{
            SQLiteDatabase dbdel=this.getWritableDatabase();
            int cursor = dbdel.delete("questions", "1", null);
            dbdel.close();

            for(int index=0;    index<jsonArray.length();   index++){
                JSONObject jsonObject=jsonArray.getJSONObject(index);
                int id=jsonObject.getInt("id");
                String question=jsonObject.getString("question");
                int is_active=jsonObject.getInt("is_active");
                int is_hr=jsonObject.getInt("is_hr");
                int mo_fmo_both=jsonObject.getInt("mo_fmo_both");
                int ques_catId=jsonObject.getInt("ques_catId");
                String ordBy=jsonObject.getString("ordBy");


                String queryInserquestions="INSERT INTO questions (id,question,is_active,is_hr,mo_fmo_both,ques_catId,ordBy) ";
                queryInserquestions=queryInserquestions+" VALUES("+id+",'"+question+"',"+is_active+","+is_hr+","+mo_fmo_both+","+ques_catId+","+ordBy+")";
                SQLiteDatabase db = this.getWritableDatabase();
                Log.i("sql1=",queryInserquestions);
                db.execSQL(queryInserquestions);
                msg="Inserted";
                db.close();
                //Toast.makeText(activity,""+msg,Toast.LENGTH_LONG).show();

            }

        }catch (Exception ex){
            msg="Error in questions:"+ex.getMessage();
            //Toast.makeText(activity,""+msg,Toast.LENGTH_LONG).show();
        }
        return msg;
    }
    public String downloadOptions(JSONArray jsonArray){
        String msg="NotInserted";
        try{
            SQLiteDatabase dbdel=this.getWritableDatabase();
            int cursor = dbdel.delete("options", "1", null);
            dbdel.close();

            for(int index=0;    index<jsonArray.length();   index++){
                JSONObject jsonObject=jsonArray.getJSONObject(index);
                int id=jsonObject.getInt("id");
                String option_name=jsonObject.getString("option_name");
                float mark=(float) jsonObject.getDouble("mark");
                int is_active=jsonObject.getInt("is_active");


                String queryInserOptions="INSERT INTO options (id,option_name,mark,is_active) ";
                queryInserOptions=queryInserOptions+" VALUES("+id+",'"+option_name+"',"+mark+","+is_active+")";
                SQLiteDatabase db = this.getWritableDatabase();
                Log.i("sql1=",queryInserOptions);
                db.execSQL(queryInserOptions);
                msg="Inserted";
                db.close();

            }

        }catch (Exception ex){
            msg="Error in questions:"+ex.getMessage();
        }
        return msg;
    }
    public String downloadQuestionsOptions(JSONArray jsonArray){
        String msg="NotInserted";
        try{
            SQLiteDatabase dbdel=this.getWritableDatabase();
            int cursor = dbdel.delete("ques_option", "1", null);
            dbdel.close();

            for(int index=0;    index<jsonArray.length();   index++){
                JSONObject jsonObject=jsonArray.getJSONObject(index);
                int id=jsonObject.getInt("id");
                String qId=jsonObject.getString("qId");
                String oId=jsonObject.getString("oId");
                String description=jsonObject.getString("description");



                String queryInserquestions="INSERT INTO ques_option (id,qId,oId,description) ";
                queryInserquestions=queryInserquestions+" VALUES("+id+","+qId+","+oId+",'"+description+"')";
                SQLiteDatabase db = this.getWritableDatabase();
                Log.i("sql1=",queryInserquestions);
                db.execSQL(queryInserquestions);
                msg="Inserted";
                db.close();

            }

        }catch (Exception ex){
            msg="Error in ques_options:"+ex.getMessage();
        }
        return msg;
    }
    public String downloadQuestionCat(JSONArray jsonArray){
        String msg="NotInserted";
        try{
            SQLiteDatabase dbdel=this.getWritableDatabase();
            int cursor = dbdel.delete("questions_category", "1", null);
            dbdel.close();

            for(int index=0;    index<jsonArray.length();   index++){
                JSONObject jsonObject=jsonArray.getJSONObject(index);
                int id=jsonObject.getInt("id");
                String ques_cat=jsonObject.getString("ques_cat");
                int is_active=jsonObject.getInt("is_active");
                int applyOn=jsonObject.getInt("applyOn");
                String ordBy=jsonObject.getString("ordBy");


                String queryInserquestions="INSERT INTO questions_category (id,ques_cat,is_active,applyOn,ordBy) ";
                queryInserquestions=queryInserquestions+" VALUES("+id+",'"+ques_cat+"',"+is_active+","+applyOn+","+ordBy+")";
                SQLiteDatabase db = this.getWritableDatabase();
                Log.i("sql1=",queryInserquestions);
                db.execSQL(queryInserquestions);
                msg="Inserted";
                db.close();

            }

        }catch (Exception ex){
            msg="Error in questions_category:"+ex.getMessage();
        }
        return msg;
    }
    public String downloadhfservices(JSONArray jsonArray){
        String msg="NotInserted";
        try{
            SQLiteDatabase dbdel=this.getWritableDatabase();
            int cursor = dbdel.delete("tbl_hf_servicesnew", "1", null);
            dbdel.close();

            for(int index=0;    index<jsonArray.length();   index++){
                JSONObject jsonObject=jsonArray.getJSONObject(index);
                int id=jsonObject.getInt("id");
                String servicesName=jsonObject.getString("servicesName");
                int is_active=jsonObject.getInt("is_active");


//id INTEGER PRIMARY KEY AUTO_INCREMENT,servicesName TEXT,is_active
                String queryInserquestions="INSERT INTO tbl_hf_servicesnew (id,servicesName,is_active) ";
                queryInserquestions=queryInserquestions+" VALUES("+id+",'"+servicesName+"',"+is_active+")";
                SQLiteDatabase db = this.getWritableDatabase();
                Log.i("sql1=",queryInserquestions);
                db.execSQL(queryInserquestions);
                msg="Inserted";
                db.close();

            }

        }catch (Exception ex){
            msg="Error in tbl_hf_servicesnew:"+ex.getMessage();
        }
        return msg;
    }
    public String downloadQTR(JSONArray jsonArray){
        String msg="NotInserted";
        try{
            SQLiteDatabase dbdel=this.getWritableDatabase();
            int cursor = dbdel.delete("p4p_quarter", "1", null);
            dbdel.close();

            for(int index=0;    index<jsonArray.length();   index++){
                JSONObject jsonObject=jsonArray.getJSONObject(index);
                int id=jsonObject.getInt("id");
                String qtr=jsonObject.getString("qtr");
                int is_running=jsonObject.getInt("is_running");

                //qtr TEXT,is_running

                String queryInserquestions="INSERT INTO p4p_quarter (id,qtr,is_running) ";
                queryInserquestions=queryInserquestions+" VALUES("+id+",'"+qtr+"',"+is_running+")";
                SQLiteDatabase db = this.getWritableDatabase();
                Log.i("sql1=",queryInserquestions);
                db.execSQL(queryInserquestions);
                msg="Inserted";
                db.close();

            }

        }catch (Exception ex){
            msg="Error in p4p_quarter:"+ex.getMessage();
        }
        return msg;
    }
    public String getQuarter(){
        String quarter="";
        try{
            String query="SELECT qtr FROM p4p_quarter where is_running=1";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            while (cursor.moveToNext()){
                quarter=cursor.getString(0);
            }
        }catch (Exception ex){
            quarter="Exception to get Quarter:"+ex.getMessage();
        }
        return quarter;
    }


    public ArrayList<Questionsbean> getQuestions(String empcode, String empdesId, Activity activity){
        ArrayList<Questionsbean> questionsbeanArrayList=new ArrayList<>();
        try{
            //String query="SELECT q.id,q.question,q.is_hr,q.mo_fmo_both,ques_catId FROM questions q WHERE q.is_active=1 AND q.id NOT IN(7)";


            String query="SELECT q.`id`,q.`question`,q.`is_hr`,q.`mo_fmo_both`,q.`ques_catId`,cat.`ques_cat` FROM questions q\n" +
                    "INNER JOIN  `questions_category` cat\n" +
                    "ON q.ques_catId=cat.`id`\n" +

                    "WHERE q.ques_catId IN (SELECT emp.`ques_catId` FROM `tbl_emp_serving` emp WHERE emp.`emp_code`='"+empcode+"')";

            if(empdesId.equals("1")){
                //Toast.makeText(activity,"AND q.mo_fmo_both in (1,3)",Toast.LENGTH_LONG).show();
                query=query+" AND q.mo_fmo_both in (1,3)";
            }else if(empdesId.equals("2")){
                //Toast.makeText(activity,"AND q.mo_fmo_both in (1,3)",Toast.LENGTH_LONG).show();
                query=query+" AND q.mo_fmo_both in (2,3)";
            }
            else if(empdesId.equals("4")){
                //Toast.makeText(activity,"AND q.mo_fmo_both in (4)",Toast.LENGTH_LONG).show();
                query=query+" AND q.mo_fmo_both in (4)";
            }
            query=query+" order by CAST(cat.ordBy as REAL), CAST(q.ordBy AS REAL)";
          // Toast.makeText(activity,"query :"+query,Toast.LENGTH_LONG).show();
            String ques="";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);

            int countlist=cursor.getCount();

            while (cursor.moveToNext()){

              //  ques+="id "+ cursor.getInt(0)+" Que: "+ cursor.getString(1) +"\n";
                questionsbeanArrayList.add(new Questionsbean(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5)));
            }
            //Toast.makeText(activity,"data: "+ques,Toast.LENGTH_LONG).show();

            cursor.close();

        }catch (Exception ex){
            Toast.makeText(activity,"Exceptions:"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return questionsbeanArrayList;
    }

    public ArrayList<Questionsbean> sortquestions(ArrayList<Questionsbean> questionsbeanArrayList){
        ArrayList<Questionsbean> tempquestionlist=new ArrayList<>();
        try{
           // questionsbeanArrayList.sort();

        }catch (Exception ex){

        }
        return tempquestionlist;
    }
    public ArrayList<OptionBean> getOptions(int qId){
        ArrayList<OptionBean> optionBeanArrayList=new ArrayList<>();
        try{
            String query="SELECT o.id,o.option_name,qo.description FROM options o \n" +
                    "INNER JOIN ques_option qo ON o.id=qo.oId WHERE qo.qId="+qId;
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            while (cursor.moveToNext()){
                optionBeanArrayList.add(new OptionBean(cursor.getInt(0),cursor.getString(2)));
            }
            cursor.close();

        }catch (Exception ex){

        }
        return optionBeanArrayList;
    }
    public ArrayList<DistrictBean> getDistricts(){
        ArrayList<DistrictBean> districtBeanArrayList=new ArrayList<>();
        try{

            //hid;HF_ID;HF_DISP_NAME;HF_CONTACT_NUM;HF_OFFICE_LEVEL;HF_PARENT_OFFICE;distType;
            String query="SELECT hid,HF_ID,HF_DISP_NAME,HF_CONTACT_NUM,HF_OFFICE_LEVEL,HF_PARENT_OFFICE,distType FROM HealthFacility h WHERE h.distType=1";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            while (cursor.moveToNext()){
                districtBeanArrayList.add(new DistrictBean(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6)));
            }
            cursor.close();

        }catch (Exception ex){

        }
        return districtBeanArrayList;
    }
    public ArrayList<DistrictBean> getHealthFacilities(int HF_PARENT_OFFICE){
        ArrayList<DistrictBean> districtBeanArrayList=new ArrayList<>();
        try{

            //hid;HF_ID;HF_DISP_NAME;HF_CONTACT_NUM;HF_OFFICE_LEVEL;HF_PARENT_OFFICE;distType;
            String query="SELECT hid,HF_ID,HF_DISP_NAME,HF_CONTACT_NUM,HF_OFFICE_LEVEL,HF_PARENT_OFFICE,distType FROM HealthFacility h WHERE HF_PARENT_OFFICE="+HF_PARENT_OFFICE+" AND  h.distType=0";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            while (cursor.moveToNext()){
                districtBeanArrayList.add(new DistrictBean(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6)));
            }
            cursor.close();

        }catch (Exception ex){

        }
        return districtBeanArrayList;
    }

    public int getEmpId(){
        int empId=0;
        try{

            //hid;HF_ID;HF_DISP_NAME;HF_CONTACT_NUM;HF_OFFICE_LEVEL;HF_PARENT_OFFICE;distType;
            String query="SELECT EMP_ID FROM tbl_Login";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            while (cursor.moveToNext()){
                empId=cursor.getInt(0);
            }
            cursor.close();

        }catch (Exception ex){

        }
        return empId;
    }


    public  int getQuarteridActive(){
        int qId=0;
        try{
            String query="SELECT id FROM p4p_quarter WHERE is_running='1'";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            while (cursor.moveToNext()){
                qId=cursor.getInt(0);
                //Toast.makeText(activity,"Empid:"+empdesId,Toast.LENGTH_LONG).show();
            }

            cursor.close();
        }catch (Exception ex){
            //Toast.makeText(activity,"Exception:"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return qId;
    }

    public String  getCode(int hfId,int phfId,int empId) {
        String msg="";
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            String childCode="";

            if(hfId!=0 && phfId!=0 && empId!=0){

                String query = "SELECT code from visit_master order by id DESC limit 1";
                Cursor c = db.rawQuery(query,null);
                if (c != null && c.moveToFirst()) {
                    String code=c.getString(0).split("-")[3];
                    Log.d("noman","noman"+code);
                    int temp=Integer.parseInt(code.toString());
                    temp++;

                    childCode=phfId+"-"+hfId+"-"+empId+"-"+temp;
                }else{
                    childCode=phfId+"-"+hfId+"-"+empId+"-"+1;
                }
                Log.d("nomanali","nomanali"+childCode);

                final Calendar calendar= Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                childCode=childCode+"-"+df.format(calendar.getTime());
                Log.d("noman","noman"+childCode);


            }else {
                childCode="You are missing Field...!";
            }
            msg=childCode;

        }catch (Exception ex){
            msg="eError:"+ex.getMessage();
        }
        return msg;
    }

    public String insertVisit(String code, int hfId, int phfId, int empId, String startTime, String endTime, int isActive, String longitude, String latitude, String imgsource, String mo_empcode, String fmo_empcode, ArrayList<Answers> questionAns, ArrayList<AttendanceBean> attendanceBeen){
        String msg="Visit has not been Submitted....!";
        try{
            msg="Visit has not been Submitted....!";
            String mo="0";
            String fmo="0";
            /*for(Answers answers:LocationsCls.questionAns){
                if(answers.getQuestionId()==5){
                    if(answers.getEmp_code()!=null && answers.getEmp_code()!=""){
                        mo=answers.getEmp_code();
                    }

                    if(answers.getOptionId()==4 || answers.getOptionId()==7){

                        String qInsertAbsentorLate="INSERT INTO attendance(vm_code,qId,oId,lateDescriptin,isempCode,emp_code,is_active,isPresent)";
                        qInsertAbsentorLate=qInsertAbsentorLate+" VALUES('"+code+"',"+answers.getQuestionId()+","+answers.getOptionId()+",'"+answers.getLateDescriptin()+"',"+answers.getIsempCode()+",'"+answers.getEmp_code()+"',"+answers.getIs_active()+","+answers.getIsPresent()+")";
                        SQLiteDatabase db = this.getWritableDatabase();
                        Log.i("sql1=", qInsertAbsentorLate);
                        db.execSQL(qInsertAbsentorLate);

                        db.close();
                    }
                }else if(answers.getQuestionId()==6){
                    if(answers.getEmp_code()!=null && answers.getEmp_code()!="") {
                        fmo = answers.getEmp_code();
                    }
                    if(answers.getOptionId()==4 || answers.getOptionId()==7){
                        String qInsertAbsentorLate="INSERT INTO attendance(vm_code,qId,oId,lateDescriptin,isempCode,emp_code,is_active,isPresent) ";
                        qInsertAbsentorLate=qInsertAbsentorLate+"  VALUES('"+code+"',"+answers.questionId+","+answers.getOptionId()+",'"+answers.getLateDescriptin()+"',"+answers.getIsempCode()+",'"+answers.getEmp_code()+"',"+answers.getIs_active()+","+answers.getIsPresent()+")";
                        SQLiteDatabase db = this.getWritableDatabase();
                        Log.i("sql1=", qInsertAbsentorLate);
                        db.execSQL(qInsertAbsentorLate);

                        db.close();
                    }
                }
            }*/

            String qMasterVisit="INSERT INTO visit_master(code,hfid,phfid,emp_id,start_time,end_time,is_active,longitude,latitude,imgsource,mo_empcode,fmo_empcode,qtr_id) ";
            qMasterVisit=qMasterVisit+" VALUES('"+code+"',"+hfId+","+phfId+","+empId+",'"+startTime+"','"+endTime+"',"+isActive+",'"+longitude+"','"+latitude+"','"+imgsource+"','"+mo_empcode+"','"+fmo+"',"+getQTRID()+")";

            SQLiteDatabase db = this.getWritableDatabase();
            Log.i("sql1=", qMasterVisit);
            db.execSQL(qMasterVisit);

            db.close();
            msg="Inserted";



            String query="SELECT id FROM visit_master order by id DESC limit 1";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            int vmId=0;
            while (cursor.moveToNext()){
                vmId=cursor.getInt(0);
            }
            cursor.close();

            if(vmId!=0) {
                for (int index = 0; index < questionAns.size(); index++) {
                    Answers answers = questionAns.get(index);
                    String queryInsertAns = "INSERT INTO visit_detail(vm_code,vm_id,qid,oid,is_active) ";
                    queryInsertAns = queryInsertAns + " VALUES('" + code + "'," + vmId + "," + answers.getQuestionId() + "," + answers.getOptionId() + ",1) ";

                    db = this.getWritableDatabase();
                    Log.i("sql1=", queryInsertAns);
                    db.execSQL(queryInsertAns);
                    db.close();
                    msg="Inserted";
                }
            }else {
                msg="Visit has not been Submitted....!";
            }

            /*if(LocationsCls.attendanceBeanArrayList.size()!=0){
                for(AttendanceBean attendanceBean:LocationsCls.attendanceBeanArrayList){
                    String qInsertAbsentorLate="INSERT INTO attendance(vm_code,qId,oId,lateDescriptin,isempCode,emp_code,is_active,isPresent)";
                    qInsertAbsentorLate=qInsertAbsentorLate+" VALUES('"+code+"',"+attendanceBean.getqId()+","+attendanceBean.getoId()+",'"+attendanceBean.getLateDescriptin()+"',"+attendanceBean.getIsempCode()+",'"+attendanceBean.getEmp_code()+"',"+attendanceBean.getIs_active()+","+attendanceBean.getIsPresent()+")";
                    db = this.getWritableDatabase();
                    Log.i("sql1=", qInsertAbsentorLate);
                    db.execSQL(qInsertAbsentorLate);
                    db.close();
                    msg="Inserted";

                }
            }*/







        }catch (Exception ex){
            msg="Error in Insert:"+ex.getMessage();
        }
        return msg;
    }


    public int countAttendance(){
        int count=0;
        try{
            String query="SELECT * FROM attendance WHERE is_active=1";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            count=cursor.getCount();
            cursor.close();
        }catch (Exception ex){

        }
        return count;
    }

    public int countMasterVisits(){
        int counVisit=0;
        try{
            String query="SELECT * FROM visit_master WHERE qtr_id <> 'null'";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            counVisit=cursor.getCount();
            cursor.close();
        }catch (Exception ex){

        }
        return counVisit;
    }
    public int CountMasterVisitsUnupload(String active){
        int counVisit=0;
        try{
            String query="SELECT * FROM visit_master WHERE is_active = '"+active+"' AND  qtr_id <> 'null'";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            counVisit=cursor.getCount();
            cursor.close();
        }catch (Exception ex){

        }
        return counVisit;
    }

    public int countDetailVisits(){
        int counVisit=0;
        try{
            String query="SELECT * FROM visit_detail";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            counVisit=cursor.getCount();
            cursor.close();
        }catch (Exception ex){

        }
        return counVisit;
    }

    public ArrayList<VisitMasterBean> uploadVisits(){
        ArrayList<VisitMasterBean> visitMasterBeanArrayList=new ArrayList<>();
        try{

            String query="SELECT code,hfid,phfid,emp_id,start_time,end_time,is_active,longitude,latitude,imgsource,mo_empcode,fmo_empcode,qtr_id FROM visit_master v WHERE v.is_active=1";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);

            while (cursor.moveToNext()){
                visitMasterBeanArrayList.add(new VisitMasterBean(cursor.getString(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12)));
            }
            cursor.close();

        }catch (Exception ex){

        }
        return visitMasterBeanArrayList;
    }

    public String getQtr(String code){
        String qtr="0";
        try{
            String query="Select qtr_id FROM visit_master v WHERE v.code='"+code+"'";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            while (cursor.moveToNext()){
                qtr=cursor.getString(0);
            }
            cursor.close();
        }catch (Exception ex){
        }
        return qtr;
    }
    public String updateVisitMaster(String code){
        String msg="notUpdated";
        try{
            String qMasterVisit="UPDATE  visit_master SET is_active=0 WHERE code='"+code+"' ";
            SQLiteDatabase db = this.getWritableDatabase();
            Log.i("sql1=", qMasterVisit);
            db.execSQL(qMasterVisit);
            db.close();
            msg="Updated";

        }catch (Exception ex){
            msg="notUpdated";
        }

        return "";
    }

    public ArrayList<VisitDetailBean> uploadVisitsDetail(){
    //public String uploadVisitsDetail(){
        ArrayList<VisitDetailBean> visitDetail=new ArrayList<>();
        String count="count:0";
        try{

            //vm_code,vm_id,qid,oid
            String query="SELECT vm_code,vm_id,qid,oid,is_active FROM visit_detail  WHERE is_active=1 OR is_Active IS NULL";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);

            while (cursor.moveToNext()){
                visitDetail.add(new VisitDetailBean(cursor.getInt(1),cursor.getString(0),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4)));
            }
            //count="Count:"+visitDetail.size();
            cursor.close();

        }catch (Exception ex){
            count="Error:"+ex.getMessage();
        }
        return visitDetail;
        //return count;
    }

    public String updateVisitMaster(String code,int qId){
        String msg="notUpdated";
        try{
            String qMasterVisitDet="UPDATE  visit_detail SET is_active=0 WHERE vm_code='"+code+"' AND qid="+qId;
            SQLiteDatabase db = this.getWritableDatabase();
            Log.i("sql1=", qMasterVisitDet);
            db.execSQL(qMasterVisitDet);
            db.close();
            msg="Updated";

        }catch (Exception ex){
            msg="notUpdated";
        }

        return msg;
    }


    public ArrayList<Tbl_emp_serving> uploadEmpservings(){
        ArrayList<Tbl_emp_serving> emp_servings=new ArrayList<>();
        String count="count:0";
        try{

            String query="SELECT id,ques_catId ,fk_hfid ,hfid ,qtr_id ,visit_masterId ,emp_code ,empname ,is_active FROM tbl_emp_serving  WHERE is_active<>0 OR is_Active IS NULL";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);

            while (cursor.moveToNext()){
                emp_servings.add(new Tbl_emp_serving(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8)));
            }
            cursor.close();

        }catch (Exception ex){
            count="Error:"+ex.getMessage();
        }
        return emp_servings;

    }

    public String updateEmpservrings(String id){
        String msg="notUpdated";
        try{
            String qAttendance="UPDATE  tbl_emp_serving SET is_active=0 WHERE id="+id;
            SQLiteDatabase db = this.getWritableDatabase();
            Log.i("sql1=", qAttendance);
            db.execSQL(qAttendance);
            db.close();
            msg="Updated";

        }catch (Exception ex){
            msg="notUpdated";
        }
        return msg;

    }



    public ArrayList<Tbl_hf_services_avail> uploadhfservices(Activity activity){
        ArrayList<Tbl_hf_services_avail> hf_services_avails=new ArrayList<>();
        String count="count:0";
        try{

            String query="SELECT id ,serviceType_id ,fk_hfid ,hfid ,qtr_id ,is_active FROM tbl_hf_services_avail  WHERE is_active<>0 OR is_Active IS NULL";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);

            while (cursor.moveToNext()){
                hf_services_avails.add(new Tbl_hf_services_avail(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
            }
            cursor.close();

        }catch (Exception ex){
            count="Error:"+ex.getMessage();
            Toast.makeText(activity,"Exception:"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return hf_services_avails;

    }
    public String updatehfservices(String id){
        String msg="notUpdated";
        try{
            String qAttendance="UPDATE  tbl_hf_services_avail SET is_active=0 WHERE id="+id;
            SQLiteDatabase db = this.getWritableDatabase();
            Log.i("sql1=", qAttendance);
            db.execSQL(qAttendance);
            db.close();
            msg="Updated";

        }catch (Exception ex){
            msg="notUpdated";
        }
        return msg;

    }




    public  String getDesignbyempid(String code,Activity activity){
        String empdesId="0";
        try{
            String query="SELECT DESIGNATION_ID FROM Employees WHERE EMP_CODE='"+code+"'";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            while (cursor.moveToNext()){
                empdesId=cursor.getString(0)+"";
                //Toast.makeText(activity,"Empid:"+empdesId,Toast.LENGTH_LONG).show();
            }

            cursor.close();
        }catch (Exception ex){
            Toast.makeText(activity,"Exception:"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return empdesId;
    }





    public  ArrayList<Employee> getEmployees(int hfId, Activity activity){
        ArrayList<Employee> employeeArrayList=new ArrayList<>();
        try{

                String query="SELECT EMP_ID, EMP_NAME || ' (' || emp_desig || ')' as EMP_NAME, MOBILE,CNIC,FK_HF_ID,PARENT_HF_ID,DESIGNATION_ID,EMP_CODE,emp_desig " +
                        "FROM Employees ORDER BY EMP_NAME";
                        //"/WHERE FK_HF_ID="+hfId;

                Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
                while (cursor.moveToNext()){
                    employeeArrayList.add(new Employee(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8)));
                }
                cursor.close();

        }catch (Exception ex){
            Toast.makeText(activity,"Error:"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return employeeArrayList;
    }

    public ArrayList<AttendanceBean> uploadAttendance(){
        ArrayList<AttendanceBean> attendanceBeanArrayList=new ArrayList<>();
        try{
            String query="SELECT vm_code,qId,oId,lateDescriptin,isempCode,emp_code,is_active,isPresent FROM attendance  WHERE is_active=1 OR is_Active IS NULL";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            while (cursor.moveToNext()){
                attendanceBeanArrayList.add(new AttendanceBean(cursor.getString(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3),cursor.getInt(4),cursor.getString(5),cursor.getInt(6),cursor.getInt(7)));
            }
            cursor.close();

        }catch (Exception ex){

        }
        return attendanceBeanArrayList;
    }

    public String updateAttendance(String code,String empCode){
        String msg="notUpdated";
        try{
            String qAttendance="UPDATE  attendance SET is_active=0 WHERE emp_code='"+code+"' AND emp_code='"+empCode+"'";
            SQLiteDatabase db = this.getWritableDatabase();
            Log.i("sql1=", qAttendance);
            db.execSQL(qAttendance);
            db.close();
            msg="Updated";

        }catch (Exception ex){
            msg="notUpdated";
        }
        return msg;
    }

    public Employee getEmployee(int hfId, int id){
        Employee emp=null;
        try{

            String query="SELECT EMP_ID, EMP_NAME, MOBILE,CNIC,FK_HF_ID,PARENT_HF_ID,DESIGNATION_ID,EMP_CODE,emp_desig FROM Employees WHERE FK_HF_ID="+hfId;
            if(id==5){
                query+=" AND DESIGNATION_ID=314 ";
            }else if(id==6){
                query+=" AND DESIGNATION_ID=325 ";
            }
            query+=" LIMIT 0,1";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            while (cursor.moveToNext()){
                emp=new Employee(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8));
                //employeeArrayList.add(new Employee(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8)));
            }
            cursor.close();



        }catch (Exception ex){

        }
        return emp;
    }

    public String activatevisits(ArrayList<String> visits){
        String msg="notactivated";
        try{
            if(visits.size()>0) {

                for (String code : visits) {

                    String qMasterVisit = "UPDATE  visit_master SET is_active=1 WHERE code='" + code + "' ";
                    SQLiteDatabase db = this.getWritableDatabase();
                    Log.i("sql1=", qMasterVisit);
                    db.execSQL(qMasterVisit);
                    db.close();


                    String qMasterVisitDet = "UPDATE  visit_detail SET is_active=1 WHERE vm_code='" + code + "'";
                    db = this.getWritableDatabase();
                    Log.i("sql1=", qMasterVisitDet);
                    db.execSQL(qMasterVisitDet);
                    db.close();
                    msg = "Activated";
                }
            }else{
                msg="You don't have visits at given date....!";
            }


        }catch (Exception ex){
            msg="Error:"+ex.getMessage();
        }
        return msg;
    }

    public ArrayList<String> getvisit(String date){
        ArrayList<String> visits=new ArrayList<>();
        try{
            String query="SELECT vm.`code` FROM `visit_master` vm WHERE date(vm.`start_time`)='"+date+"'";
            Cursor cursor = this.getReadableDatabase().rawQuery(query,null);
            while (cursor.moveToNext()){
                visits.add(cursor.getString(0));
            }
            cursor.close();
        }catch (Exception ex){

        }
        return visits;
    }

}