package ua.nure.kravchenko.service;

import org.springframework.web.bind.annotation.PathVariable;
import ua.nure.kravchenko.entity.Balance;
import ua.nure.kravchenko.entity.Location;
import ua.nure.kravchenko.entity.RoleEntity;
import ua.nure.kravchenko.entity.UserEntity;
import ua.nure.kravchenko.entity.dto.UserDTO;
import ua.nure.kravchenko.entity.project.Payment;
import ua.nure.kravchenko.entity.project.Roles;
import ua.nure.kravchenko.entity.project.SalaryTypes;
import ua.nure.kravchenko.entity.project.Statistic;
import ua.nure.kravchenko.repository.BalanceRepository;
import ua.nure.kravchenko.repository.PaymentRepository;
import ua.nure.kravchenko.repository.RoleEntityRepository;
import ua.nure.kravchenko.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserEntityRepository userEntityRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final BalanceRepository balanceRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public UserService(UserEntityRepository userEntityRepository, RoleEntityRepository roleEntityRepository, PasswordEncoder passwordEncoder, BalanceRepository balanceRepository, PaymentRepository paymentRepository) {
        this.userEntityRepository = userEntityRepository;
        this.roleEntityRepository = roleEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.balanceRepository = balanceRepository;
        this.paymentRepository = paymentRepository;
    }

    public UserEntity saveUser(UserEntity userEntity) {
        RoleEntity userRole = roleEntityRepository.findByName("ROLE_USER");
        userEntity.setRoleEntity(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepository.save(userEntity);
    }

    public UserEntity findById(int id) {
        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        if(userEntity.isPresent()){
            return  userEntity.get();
        }
        return new UserEntity();
    }

    public boolean checkBalanceExist(UserEntity user){
        if(user.getBalance()!=null) {
            user.getBalance().setRequest(0);
            return true;
        }
        return false;
    }

    public UserEntity acceptPaymentRequest(UserEntity userEntity){
        if(checkBalanceExist(userEntity)){
            Balance balance = userEntity.getBalance();
            balance.setBalance(balance.getBalance() + balance.getRequest());
            balance.setRequest(0);
            userEntity.setBalance(balance);
            return userEntity;
        }
        return null;
    }


    public UserEntity setLocationToUser(UserEntity userEntity, Location location){
        if(checkRole(userEntity)==true){
            userEntity.setLocation(location);
        }
        return userEntity;
    }

    public boolean checkRole(UserEntity userEntity){
        if(userEntity.getRoleEntity().getName().equals(Roles.ROLE_USER.getName())){
            return true;
        }
        return false;
    }

    public UserEntity updateUser(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    public UserEntity findByLogin(String login) {
        return userEntityRepository.findByLogin(login);
    }

    public UserEntity findByLoginAndPassword(String login, String password) {
        UserEntity userEntity = findByLogin(login);
        if (userEntity != null) {
            System.out.println(11111111);
            System.out.println(userEntity.getPassword() + "ddd");
            return userEntity;
//            if (passwordEncoder.matches(password, userEntity.getPassword())) {
//                System.out.println(2222);
//                return userEntity;
//            }
        }
        return new UserEntity();
    }

    public List<UserDTO> findListByRoleName(String roleName) {
        List<UserEntity> userEntities =  userEntityRepository.findUserEntitiesByRoleEntityName(roleName);
        List<UserDTO> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(new UserDTO(userEntity));
        }
        return users;
    }

    public List<UserEntity> findAll() {
        return userEntityRepository.findAll();
    }

    public UserEntity deleteUserLocation(int userId) throws Exception {
        Optional<UserEntity> userEntityOptional =  userEntityRepository.findById(userId);
        if(userEntityOptional.isPresent()){
            UserEntity user = userEntityOptional.get();
            user.setLocation(null);
            return userEntityRepository.save(user);
        }else {
            throw new Exception("No such user with this id");
        }
    }

    public Location getLocation(@PathVariable int id){
        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        if(userEntity.isPresent()){
            UserEntity  user = userEntity.get();
            if(user.getLocation()!=null){
                return user.getLocation();
            }
        }
        return new Location();
    }


    private double range(UserEntity userEntity){
        double userBalanceVal = userEntity.getBalance().getBalance();
        if(userBalanceVal <= SalaryTypes.MIN.getSalary()){
            return SalaryTypes.MIN.getCoefficient();
        }else if(userBalanceVal > SalaryTypes.MEDIUM.getSalary() && userBalanceVal <= SalaryTypes.MEDIUM.getSalary()){
            return SalaryTypes.MEDIUM.getCoefficient();
        }else{
            return SalaryTypes.MAX.getCoefficient();
        }
    }

    public Balance acceptPaymentRequest(int workerId, int managerId) throws Exception {
        UserEntity worker = this.findById(workerId);
        if (worker.getBalance() != null) {
            UserEntity manager = this.findById(managerId);
            double coefficient = range(worker);
            Balance workerBalance =  worker.getBalance();
            Balance managerBalance =  manager.getBalance();
            double sum1 = workerBalance.getRequest()*coefficient;
            double sum2 = workerBalance.getRequest() - workerBalance.getRequest()*coefficient;
            managerBalance.setBalance(manager.getBalance().getBalance() + workerBalance.getRequest()*coefficient);
            workerBalance.setBalance(workerBalance.getBalance() + workerBalance.getRequest() - workerBalance.getRequest()*coefficient);
            workerBalance.setRequest(0);
            balanceRepository.save(managerBalance);
            balanceRepository.save(workerBalance);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Payment paymentManager = new Payment();
            paymentManager.setDate(timestamp);
            paymentManager.setBalance(managerBalance);
            paymentManager.setAcceptStatus(true);
            paymentManager.setMoney(sum1);
            paymentRepository.save(paymentManager);
            Payment paymentWorker = new Payment();
            paymentWorker.setDate(timestamp);
            paymentWorker.setBalance(workerBalance);
            paymentWorker.setAcceptStatus(true);
            paymentWorker.setMoney(sum2);
            paymentRepository.save(paymentWorker);
            return workerBalance;
        }
        throw new Exception("User don't have balance");
    }

    public Balance generateCompensation(UserEntity user, Statistic statistic){
        Balance balance = user.getBalance();
        double markAverage = statistic.getMarkAverage();
        double compensation;
        if(markAverage <= 5){
            compensation =  balance.getRequest() + markAverage * 25;
        }else if(markAverage > 5 && markAverage < 7){
            compensation =  balance.getRequest() + markAverage * 30;
        }else if(markAverage > 7 && markAverage < 9){
            compensation = balance.getRequest() + markAverage * 35;
        }else{
            compensation = balance.getRequest() + markAverage * 40;
        }
        balance.setRequest(compensation);
        return balance;
    }


}
