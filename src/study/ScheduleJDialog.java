package study;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;


import java.text.SimpleDateFormat;

import javax.swing.*;



public class ScheduleJDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	JPanel pw=new JPanel(new GridLayout(2,1));
    JPanel pc=new JPanel(new GridLayout(2,1));
    JPanel ps=new JPanel();
 
    JLabel lable_Date = new JLabel("날짜");
    JLabel lable_Note=new JLabel("일정");
 
 
    JTextField date=new JTextField();
    JTextField note=new JTextField();
   
    JButton confirm,delete;
    JButton reset=new JButton("취소");

    ScheduleDAO sDao=new ScheduleDAO();
	
	CalendarFr cal;
	
	public ScheduleJDialog(CalendarFr cal, String index){
		super(cal, "dialog");
		this.cal=cal;
		
		if(index.equals("등록")){
			confirm=new JButton(index);
		}else{
			confirm=new JButton(index);
			delete=new JButton("삭제");
			ps.add(delete);
			int row=cal.jt.getSelectedRow();
			date.setText(cal.jt.getValueAt(row, 1).toString());
			note.setText(cal.jt.getValueAt(row, 2).toString());
			
			date.setEditable(false);
		}
		
		pw.add(lable_Date);
		pw.add(lable_Note);
		
		pc.add(date);
		pc.add(note);
		
		ps.add(confirm);
		ps.add(reset);
		
		add(pw,"West");
		add(pc,"Center");
		add(ps,"South");
		
		 setSize(300,200);
	     setVisible(true);
	 
	     setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	     
	     confirm.addActionListener(this);
	     reset.addActionListener(this);
	     delete.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String btn=e.getActionCommand();
		
		if(btn.equals("등록")){
			insertDialog();
		}else if(btn.equals("수정")){
			updateDialog();
		}else if(btn.equals("삭제")){
			System.out.println("버튼 클릭");
			deleteDialog();
		}else if(btn.equals("취소")){
			dispose();
		}
	}
	
	
	public void messageBox(Object obj,String message){
		JOptionPane.showMessageDialog((Component) obj, message);
	}
	
	public ScheduleDTO getViewData(){
		ScheduleDTO sDto=new ScheduleDTO();
		sDto.setDate(date.getText());
		sDto.setNote(note.getText());
		
		return sDto;
	}
	public ScheduleDTO getViewData(int pk_num){
		ScheduleDTO sDto=new ScheduleDTO();
		sDto.setPk_num(pk_num);
		sDto.setDate(date.getText());
		sDto.setNote(note.getText());
		
		return sDto;
	}
	
	public void insertDialog(){
		ScheduleDTO sDto=getViewData();
		ScheduleDAO sDao=new ScheduleDAO();
		int result=sDao.insert(sDto);
		if(result>0){
			JOptionPane.showMessageDialog(this, "일정등록이 완료되었습니다.");
			dispose();
		}else{
			JOptionPane.showMessageDialog(this, "일정등록이 정상적으로 되지 않았습니다.");
		}
		
		cal.JTableRefresh();
	}
	
	public void updateDialog(){
		ScheduleDTO sDto=getViewData((int) cal.jt.getValueAt(cal.jt.getSelectedRow(), 0));
		ScheduleDAO sDao=new ScheduleDAO();
		System.out.println("dao에 들어감");
		int result=sDao.update(sDto);
		if(result>0){
			JOptionPane.showMessageDialog(this, "수정이 완료되었습니다.");
			dispose();
		}else{
			JOptionPane.showMessageDialog(this, "수정이 정상적으로 되지 않았습니다.");
		}
		cal.JTableRefresh();
	}
	
	public void deleteDialog() {
		ScheduleDTO sDto=getViewData((int) cal.jt.getValueAt(cal.jt.getSelectedRow(), 0));
		ScheduleDAO sDao=new ScheduleDAO();
		int result=sDao.deleteDialog(sDto);
		
		if(result>0){
			JOptionPane.showMessageDialog(this, "삭제가 완료되었습니다.");
			dispose();
		}else{
			JOptionPane.showMessageDialog(this, "삭제가 정상적으로 되지 않았습니다.");
		}
		cal.JTableRefresh();
	}

	


}
