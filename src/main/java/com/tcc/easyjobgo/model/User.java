package com.tcc.easyjobgo.model;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tcc.easyjobgo.factory.RoleFactory;
import com.tcc.easyjobgo.repository.IRoleRepository;

public class User implements UserDetails{

    private UUID id;

    private String cpf;

    private String firstName;
    private String lastName;

    private String username; /*EMAIL*/
    private String password;

    private String numberDDD;
    private String number;
    
    private Date birthdate;
    
    private String cep;
    private String addressDistrict;
    private String addressPubPlace;
    private Object addressComp;
    
    private String city;
    private String fedUnit;

    private Date registrationDate;

    private boolean provideService;
    private Object serviceValue;

    private Object serviceCategoryId;
    private Object subserviceCategoryId;

    private Integer statusId;

    private Integer roleId;

    public User(){
        
    }
    public User(UUID id, String cpf, String firstName, String lastName, String username, String password,
                String numberDDD, String number, Date birthdate, String cep, String addressDistrict,
                String addressPubPlace, Object addressComp, String city, String fedUnit, Date registrationDate,
                boolean provideService, Object serviceValue, Object serviceCategoryId, 
                Object subserviceCategoryId, Integer statusId, Integer roleId){
        
        this.id = id;
        this.cpf = cpf;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.numberDDD = numberDDD;
        this.number = number;
        this.birthdate = birthdate;
        this.cep = cep;
        this.addressDistrict = addressDistrict;
        this.addressPubPlace = addressPubPlace;
        this.addressComp = addressComp;
        this.city = city;
        this.fedUnit = fedUnit;
        this.registrationDate = registrationDate;
        this.provideService = provideService;
        this.serviceValue = serviceValue;
        this.serviceCategoryId = serviceCategoryId;
        this.subserviceCategoryId = subserviceCategoryId;
        this.statusId = statusId;
        this.roleId = roleId;
    }

    public UUID getId(){
        return id;
    }

    public String getCpf(){
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public String getNumberDDD(){
        return numberDDD;
    }
    public void setNumberDDD(String numberDDD){
        this.numberDDD = numberDDD;
    }
    
    public String getNumber(){
        return number;
    }
    public void setNumber(String number){
        this.number = number;
    }
    
    public Date getBirthdate(){
        return birthdate;
    }
    public void setBirthdate(Date birthdate){
        this.birthdate = birthdate;
    }
    
    public String getCep(){
        return cep;
    }
    public void setCep(String cep){
        this.cep = cep;
    }
    
    public String getAddressDistrict(){
        return addressDistrict;
    }
    public void setAddressDistrict(String addressDistrict){
        this.addressDistrict = addressDistrict;
    }
    
    public String getAddressPubPlace(){
        return addressPubPlace;
    }
    public void setAddressPubPlace(String addressPubPlace){
        this.addressPubPlace = addressPubPlace;
    }
    
    public Object getAddressComp(){
        return addressComp;
    }
    public void setAddressComp(Object addressComp){
        this.addressComp = addressComp;
    }
    
    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }
    
    public String getFedUnit(){
        return fedUnit;
    }
    public void setFedUnit(String fedUnit){
        this.fedUnit = fedUnit;
    }
    
    public Date getRegistrationDate(){
        return registrationDate;
    }
    public void setRegistrationDate(Date registrationDate){
        this.registrationDate = registrationDate;
    }
    
    public boolean isProvideService(){
        return provideService;
    }
    public void setProvideService(boolean provideService){
        this.provideService = provideService;
    }
    
    public Object getServiceValue(){
        return serviceValue;
    }
    public void setServiceValue(Object serviceValue){
        this.serviceValue = serviceValue;
    }

    public Object getServiceCategoryId(){
        return serviceCategoryId;
    }
    public void setServiceCategoryId(Object serviceCategoryId){
        this.serviceCategoryId = serviceCategoryId;
    }
    
    public Object getSubserviceCategoryId(){
        return subserviceCategoryId;
    }
    public void setSubserviceCategoryId(Object subserviceCategoryId){
        this.subserviceCategoryId = subserviceCategoryId;
    }

    public Integer getStatusId(){
        return statusId;
    }
    public void setStatusId(Integer statusId){
        this.statusId = statusId;
    }

    public Integer getRoleId(){
        return roleId;
    }
    public void setRoleId(Integer roleId){
        this.roleId = roleId;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        IRoleRepository roleRepository = RoleFactory.createRoleService();
        Role role = roleRepository.findRoleById(this.roleId);

        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role.getDescription()); 
        return Collections.singletonList(auth);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        
        boolean status = false;

        switch(this.statusId){
            case 1:
            status = true;
            break;
            case 2:
            status = false;
            break;
            case 3:
            status = false;
            break;
        }
        
        return status;
    }
}
