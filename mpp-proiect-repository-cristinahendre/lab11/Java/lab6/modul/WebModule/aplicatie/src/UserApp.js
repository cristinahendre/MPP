
import React from  'react';
import CazTable from './Caz';
import './UserApp.css'
import UserForm2 from "./UserForm2";
import UpdateForm from "./UpdateForm";

import {GetAll, DeleteOne, AddOne, UpdateOne} from './calls'


class UserApp extends React.Component{
    constructor(props){
        super(props);
        this.state={users:GetAll(),
            deleteFunc:this.deleteFunc.bind(this),
            addFunc:this.addFunc.bind(this),
            updateFunc:this.updateFunc.bind(this),
        }
        console.log('UserApp constructor')
    }

    addFunc(user){
        console.log('inside add Func '+user);
        AddOne(user)
            .then(res=> GetAll())
            .then(users=>this.setState({users}))
            .catch(erorr=>console.log('eroare add ',erorr));
    }

    updateFunc(user){
        console.log("update func = "+user);
        UpdateOne( user)
            .then(res=> GetAll())
            .then(users=>this.setState({users}))
            .catch(erorr=>console.log('eroare update ',erorr));
    }


    deleteFunc(user){
        console.log('inside deleteFunc '+user);
        DeleteOne(user)
            .then(res=> GetAll())
            .then(users=>this.setState({users}))
            .catch(error=>console.log('eroare delete', error));
    }


    componentDidMount(){
        console.log('inside componentDidMount')
        GetAll().then(users=>this.setState({users}));
    }

    render(){
        return(
            <div className="UserApp">
                <h1>Cazuri Caritabile</h1>
                <UserForm2 addFunc={this.state.addFunc} /><br/>
                <UpdateForm updateFunc={this.state.updateFunc} />
                {/*<UserForm updateFunc={this.state.updateFunc}/>*/}
                <br/>
                <br/>
                <CazTable users={this.state.users} deleteFunc={this.state.deleteFunc}/>
            </div>
        );
    }
}

export default UserApp;