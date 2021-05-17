package jxrwxz.teachassistant.data;

import jxrwxz.teachassistant.Student;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public interface StudentRepository extends CrudRepository<Student,Long> {
    Student findByNameAndPassword(String name, String password);

    @Transactional
    @Modifying
    @Query(value = "update student set PASSWORD=?1 where ID=?2 ",nativeQuery = true)
    public void changePassword(String password,long sid);

    @Transactional
    @Modifying
    @Query(value = "select * from student limit ?1,?2 ",nativeQuery = true)
    public List<Student> findAll(int offset, int limit);

    @Transactional
    @Modifying
    @Query(value = "update student set NAME=?1,PASSWORD=?2  where ID=?3 ",nativeQuery = true)
    public void updateStudent(String name,String password,long id);

    @Query(value = "select count(*) from student where NAME=?1 ",nativeQuery = true)
    public int selectByName(String name);

    @Transactional
    @Modifying
    @Query(value = "insert into student(NAME,PHONE_NUMBER,SCHOOL_NAME) VALUES (?1,?2,?3)",nativeQuery = true)
    public void addStudent(String name,String PhoneNumber,String SchoolName);

    @Transactional
    @Modifying
    @Query(value = "update student set PHONE_NUMBER=?2,SCHOOL_NAME=?3  where NAME=?1 ",nativeQuery = true)
    public void updateStudentByName(String name,String PhoneNumber,String SchoolName);


    @Transactional
    default  boolean batchImport(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;
        List<Student> studentList = new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        Student student;
        for (int r = 2; r <= sheet.getLastRowNum(); r++) {//r = 2 表示从第三行开始循环 如果你的第三行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到 行对象
            if (row == null){
                continue;
            }

            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException

            student = new Student();

            if( row.getCell(0).getCellType() !=1){//循环时，得到每一行的单元格进行判断
                throw new Exception("导入失败(第"+(r+1)+"行,用户名请设为文本格式)");
            }

            String Studentname = row.getCell(0).getStringCellValue();//得到每一行第一个单元格的值


            if(Studentname == null || Studentname.isEmpty()){//判断是否为空
                throw new Exception("导入失败(第"+(r+1)+"行,学生名未填写)");
            }


            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值
            String phonenumber = row.getCell(1).getStringCellValue();


            if(phonenumber==null || phonenumber.isEmpty()){
                throw new Exception("导入失败(第"+(r+1)+"行,手机未填写)");
            }

            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值
            String schoolname = row.getCell(2).getStringCellValue();


            if(schoolname==null || schoolname.isEmpty()){
                throw new Exception("导入失败(第"+(r+1)+"行,学校未填写)");
            }


            //完整的循环一次 就组成了一个对象
            student.setName(Studentname);
            student.setPhoneNumber(phonenumber);
            student.setSchoolName(schoolname);
            studentList.add(student);
        }
        for (Student studentResord : studentList) {
            String name = studentResord.getName();
            String PhoneNumber = studentResord.getPhoneNumber();
            String SchoolName = studentResord.getSchoolName();
            int cnt = this.selectByName(name);
            if (cnt == 0) {
                this.addStudent(name,PhoneNumber,SchoolName);
                System.out.println(" 插入 "+studentResord);
            } else {
                this.updateStudentByName(name,PhoneNumber,SchoolName);
                System.out.println(" 更新 "+studentResord);
            }
        }
        return notNull;
    }

}
