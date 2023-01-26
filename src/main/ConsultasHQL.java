package main;

import modelo.Departamento;
import modelo.Emp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.SessionFactoryUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/*








5. Los nombres de los departamentos cuyos ids se encuentren entre el 10 o el 20. Usa una lista parametrizada.

6. El número de empleados por departamento
 */

public class ConsultasHQL {
    public static void main(String[] args) {
        SessionFactory sessionFactory= SessionFactoryUtil.getSessionFactory();
        Session session= sessionFactory.openSession();
        {
            //1. El nombre, el puesto de trabajo y el salario de todos los empleados
            System.out.println("1. El nombre, el puesto de trabajo y el salario de todos los empleados");
            List<Object[]> lista=session.createQuery("SELECT e.ename, e.job, e.sal from Emp e").list();
            for (Object[] e:lista) {
                System.out.println("Nombre: "+e[0]+", Puesto trabajo:" +e[1]+ ", Salario: "+e[2]);
            }


        }

        {
            //2. La media del salario de todos los empleados
            System.out.println("\n2. La media del salario de todos los empleados");
            double mediaSalario=(double) session.createQuery("SELECT AVG(sal) from Emp e").uniqueResult();
            System.out.println("La media de sueldo de los trabajadores es "+mediaSalario);
        }

        {
            //3. Los empleados que tengan un salario mayor que la media de todos los empleados
            System.out.println("\n3. Los empleados que tengan un salario mayor que la media de todos los empleados");
            List<Emp> lista=session.createQuery("SELECT e from Emp e where e.sal>(SELECT avg(e2.sal) from Emp e2)").list();
            for (Emp e:lista) {
                System.out.println(e);
            }

        }

        {
            //4. Los departamentos con un determinado id con parámetros nominales y posicionales
            System.out.println("\n4. Los departamentos con un determinado id con parámetros nominales y posicionales");
            int idDept=40;
            System.out.println("Departamento por NOMBRE");
            Departamento departamento= (Departamento) session.createQuery("SELECT d from Departamento d where d.deptno=:id").setParameter("id", idDept).uniqueResult();
            System.out.println(departamento);

            System.out.println("Departamento por POSICION");
            Departamento departamento2= (Departamento) session.createQuery("SELECT d from Departamento d where d.deptno=?0").setParameter(0, idDept).uniqueResult();
            System.out.println(departamento2);
        }

        {
            //5. Los nombres de los departamentos cuyos ids se encuentren entre el 10 o el 20. Usa una lista parametrizada.
            System.out.println("\n5. Los nombres de los departamentos cuyos ids se encuentren entre el 10 o el 20. Usa una lista parametrizada.");
            List<Integer> lista=new ArrayList<>();
            lista.add(10);
            lista.add(20);
            List<String> consulta=session.createQuery("SELECT d.dname from Departamento d where d.deptno in :listado").setParameterList("listado",lista).list();
            for (String d:consulta) {
                System.out.println(d);
            }
        }

        {
            //6. El número de empleados por departamento
            System.out.println("\n6. El número de empleados por departamento");
            List<Object[]> lista=session.createQuery("select d.dname, count(d.deptno) from Departamento d group by d.dname").list();
            for (Object[] obj:lista) {
                System.out.println(obj[0]+ ": "+obj[1]);
            }

        }
    }
}
