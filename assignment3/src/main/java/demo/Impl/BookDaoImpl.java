package demo.Impl;
import demo.BookDao;
public class BookDaoImpl implements BookDao{
    @Override
    public void save(){
        System.out.println("book dao save ...");
    }
}
