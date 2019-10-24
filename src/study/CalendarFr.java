package study;

import java.awt.Container;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CalendarFr extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	Container contentPane;
	Vector v;
	Vector cals;
	DefaultTableModel model;
	JTable jt;
	JScrollPane jsp;
	
	JButton insert=new JButton("일정등록");
	JButton update=new JButton("수정");

	String[] monthC = {"선택", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
			"12" };
	
	Calendar cal=Calendar.getInstance();
	int month=cal.get(Calendar.MONTH); //달은 0에서 부터 시작하므로 10월->9 입력
	int year=cal.get(Calendar.YEAR);
	
	public CalendarFr() {

		setTitle("Calendar&Schedule");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane=getContentPane();
		
		contentPane.setLayout(null);
		
		//일정 리스트
				ScheduleDAO sDao=new ScheduleDAO();
				v=sDao.select();
				cals=getColumm();
				
				model=new DefaultTableModel(v,cals);
				
				jt=new JTable(model);
				/*jsp=new JScrollPane(jt);*/
				jt.setSize(525,200);
				jt.setLocation(50,400);
				add(jt);
		
		//달 선택
	/*	JComboBox com=new JComboBox(monthC);
		
		com.setSize(80,30);
		com.setLocation(460,40);
		MyActionListener mcl=new MyActionListener();
		com.addActionListener(mcl);
		add(com);*/
				
		insert.setSize(90,30);
		update.setSize(90,30);
		
		insert.setLocation(50,360);
		update.setLocation(150,360);
		
		insert.addActionListener(this);
		update.addActionListener(this);
		
		add(insert);
		add(update);
		
		
		MyCal(year,month);

		setSize(650,720);
		setVisible(true);
		
	}
	public Vector getColumm(){
		Vector cal=new Vector();
		
		cal.add("고유번호");
		cal.add("날짜");
		cal.add("일정");
		return cal;
	}
	
	public void JTableRefresh(){
		ScheduleDAO sDao=new ScheduleDAO();
		DefaultTableModel model=new DefaultTableModel(sDao.select(),getColumm());
		jt.setModel(model);
		
	}
	
	public void MyCal(int year, int month){
		
		cal.set(year, month, 1); 
		
		int end=cal.getActualMaximum(Calendar.DATE);
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		int dayOfWeekF=8-dayOfWeek;
		
		JLabel jl=new JLabel(Integer.toString(year)+"년    "+Integer.toString(month+1)+"월");
		jl.setLocation(350, 40);
		jl.setSize(80,30);
		add(jl);
		
        String[] day = {"일","월","화","수","목","금","토"};
        for(int i=0 ; i<7 ; i++){
        	String dayJ=day[i];
        	JButton b2=new JButton(dayJ);
        	b2.setLocation((i+1)*70,100);
        	b2.setSize(70,40);
            add(b2);
        }
		int height=180;
		int width=70;
		int num=1;
		for(int i=1;i<=end;i++){
			if(i<=(dayOfWeekF)){
				JButton b=new JButton(Integer.toString(i));
				b.setLocation(((dayOfWeek-1)*70)+i*70,140);
				b.setSize(70,40);
				add(b);
			}else{
					JButton jb=new JButton(Integer.toString(i));
					jb.setLocation(num*width,height);
					jb.setSize(70,40);
					add(jb);
					num+=1;
				if(((i-1)+dayOfWeek)%7==0){
					height+=40;
					width=70;
					num=1;	
				}
		}}
	}
	
		public void actionPerformed(ActionEvent e) {
			/*JComboBox cb=(JComboBox)e.getSource();
			
			int index=cb.getSelectedIndex();
			
			MyCal(year,index);*/
			
			if(e.getSource()==insert){
				new ScheduleJDialog(this,"등록");
			}else if(e.getSource()==update){
				if(jt.getSelectedRowCount()==0){
					JOptionPane.showMessageDialog(this, "수정할 내용을 선택해주세요.");
				}else{
					new ScheduleJDialog(this,"수정");
				}
			}
		}


	public static void main(String[] args) {

		new CalendarFr();

	}

}


