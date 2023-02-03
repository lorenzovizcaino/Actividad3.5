package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.SessionFactoryUtil;

import java.util.List;

public class ConsultasAsociacionesHQL {
    public static void main(String[] args) {
        SessionFactory sessionFactory= SessionFactoryUtil.getSessionFactory();
        Session session= sessionFactory.openSession();

        {
            System.out.println("1.- Los nombres de los departamentos que no tengan empleados. " +
                    "Los departamentos deben ser ordenados por nombre");

            List<String> lista=session.createQuery(
                    //"select d.dname from Departamento d left join d.emps e where d.emps is empty order by d.dname"
                    " select d.dname FROM Departamento d where size (d.emps) =0 order by d.dname  "
            ).list();
            for (String s:lista) {
                System.out.println(s);
            }
        }

        {
            System.out.println("2.Los nombres de los departamentos y de los empleados que tienen al menos 2 empleados. " +
                    "El resultado debe ser ordenado por nombre de departamento");

            List<Object[]> lista=session.createQuery("select d.dname, e.ename" +
                    " FROM Departamento d join d.emps e where size (d.emps) >=2  order by d.dname").list();
            for (Object[] e:lista) {
                System.out.println("Departamento: "+e[0]+", Empleado:" +e[1]);
            }
        }

        {
            System.out.println("3.Los identificadores de los empleados y el nº de cuentas por empleado");
            List<Object[]>lista=session.createQuery("select e.empno, count(a.accountno) from Emp e left join e.accounts a group by e.empno").list();
            for (Object[] e:lista) {
                System.out.println("idEmpleado: "+e[0]+ " Num. de Cuentas "+e[1]);
            }


        }

        {
            System.out.println("4.Los identificadores de los empleados y el saldo de sus cuentas");
            List<Object[]>lista=session.createQuery("select e.empno, sum(a.amount) from Emp e join e.accounts a group by e.empno").list();
            for (Object[] e:lista) {
                System.out.println("idEmpleado: "+e[0]+ " saldo "+e[1]);
            }
        }

        {
            System.out.println("5.El identificador de cada cuenta con el identificador del movimiento donde la cuenta es la cuenta origen");



        }

        {
            System.out.println("6.El nº de movimientos por cada cuenta origen");
            List<Object[]> lista=session.createQuery("select a.accountno, count(am.accountOriginId) from Account a left join a.accMovements am group by a.accountno").list();
            for (Object[] e:lista) {
                System.out.println("Numero Cuenta: "+e[0]+" Numero de Movimientos "+e[1]);
            }



        }

        {
            System.out.println("7. El nombre de cada empleado con el de su jefe. Ha de aparecer el nombre del empleado aunque no tenga jefe");




        }




    }
}
