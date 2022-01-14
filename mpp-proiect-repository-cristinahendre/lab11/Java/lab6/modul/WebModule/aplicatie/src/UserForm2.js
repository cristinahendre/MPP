
import React from  'react';
class UserForm2 extends React.Component {

    constructor(props) {
        super(props);
        this.state = {nume: '', suma_donata: ''};

        //  this.handleChange = this.handleChange.bind(this);
        // this.handleSubmit = this.handleSubmit.bind(this);
    }



    handleNumeChange = (event) => {
        this.setState({nume: event.target.value});
    }

    handleSumaChange = (event) => {
        this.setState({suma_donata: event.target.value});
    }
    handleSubmit = (event) => {

        var user = {

            nume: this.state.nume,
            suma_donata: this.state.suma_donata
        }
        console.log('A caz was submitted: ');
        console.log(user);
        this.props.addFunc(user);
        event.preventDefault();
    }


    handleUpdate = (event) => {

        var user = {

            nume: this.state.nume,
            suma_donata: this.state.suma_donata
        }
        console.log('A caz was sent to update: ');
        console.log(user);
        this.props.updateFunc(user);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>

                <label>
                    Nume:
                    <input type="text" value={this.state.nume} onChange={this.handleNumeChange}/>
                </label><br/>
                <label>
                    Suma:
                    <input type="suma_donata" value={this.state.suma_donata} onChange={this.handleSumaChange}/>
                </label><br/>

                <input type="submit" value="Submit"/>

            </form>
        );
    }
}
export default UserForm2;