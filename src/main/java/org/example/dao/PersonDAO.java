package org.example.dao;

import org.example.model.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO extends BaseDAO<Person>{

    public PersonDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Person element) throws SQLException {
        request = "insert into person (first_name,last_name)values(?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,element.getFirstName());
        statement.setString(2,element.getLastName());
        int nbRows=statement.executeUpdate();
        resultSet=statement.getGeneratedKeys();
        if (resultSet.next()){
            element.setId(resultSet.getInt(1));
        }

        return nbRows==1;
    }

    @Override
    public boolean update(Person element) throws SQLException {
        request = "update person set first_name=? , last_name=? where id=?";
        statement = _connection.prepareStatement(request);
        statement.setString(1,element.getFirstName());
        statement.setString(2,element.getLastName());
        statement.setInt(3,element.getId());

        int nbRows=statement.executeUpdate();
        return nbRows==1;

    }

    @Override
    public boolean delete(Person element) throws SQLException {
        request = "delete from person where id=?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1,element.getId());
        int nbRows=statement.executeUpdate();
        return nbRows==1;

    }

    @Override
    public Person get(int id) throws SQLException {
        Person person=null;
        request = "select * from person where id=?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1,id);
        int nbRows=statement.executeUpdate();
        if (resultSet.next()){
            int ide=resultSet.getInt("id");
            String firstName=resultSet.getString("first_name");
            String last_name=resultSet.getString("last_name");
            person=new Person(ide,firstName,last_name);

        }
        return person;
    }

    @Override
    public List<Person> get() throws SQLException {
        List<Person> resulta = new ArrayList<>();
        request = "select * from person";
        statement = _connection.prepareStatement(request);
        int nbRows=statement.executeUpdate();
        if (resultSet.next()){
            int id=resultSet.getInt("id");
            String firstName=resultSet.getString("first_name");
            String last_name=resultSet.getString("last_name");
            Person person=new Person(id,firstName,last_name);
            resulta.add(person);
        }
        return resulta;
    }
}
