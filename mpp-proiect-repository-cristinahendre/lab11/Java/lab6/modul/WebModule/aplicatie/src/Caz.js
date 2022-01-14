
import React from  'react';
import './UserApp.css'

class CazRow extends React.Component{

    handleClicke=(event)=>{
        console.log('delete button pentru '+this.props.user.id);
        this.props.deleteFunc(this.props.user.id);
    }

    render() {
        return (
            <tr>
                <td>{this.props.user.id}</td>
                <td>{this.props.user.nume}</td>
                <td>{this.props.user.suma_donata}</td>
                <td><button  onClick={this.handleClicke}>Delete</button></td>
            </tr>
        );
    }
}
/*<form onSubmit={this.handleClicke}><input type="submit" value="Delete"/></form>*/
/**/
class CazTable extends React.Component {
    render() {
        var rows = [];
        var functieStergere=this.props.deleteFunc;
        const parent = this.props.users;

        Array.prototype.forEach.call(parent, user => {
            rows.push(<CazRow user={user} key={user.id} deleteFunc={functieStergere} />);

        });
        // this.props.users.forEach(function(user) {
        //
        //     rows.push(<CazRow user={user} key={user.id} deleteFunc={functieStergere} />);
        // });
        return (<div className="UserTable">

                <table className="center">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Nume</th>

                        <th>Suma</th>
                    </tr>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>

            </div>
        );
    }
}

export default CazTable;