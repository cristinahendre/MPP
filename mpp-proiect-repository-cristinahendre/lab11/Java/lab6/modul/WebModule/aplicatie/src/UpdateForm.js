
import React from  'react';
class UpdateForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {id: '', nume: '', suma_donata: ''};

        //  this.handleChange = this.handleChange.bind(this);
        // this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleIdChange = (event) => {
        this.setState({id: event.target.value});
    }

    handleNumeChange = (event) => {
        this.setState({nume: event.target.value});
    }

    handleSumaChange = (event) => {
        this.setState({suma_donata: event.target.value});
    }


    handleUpdate = (event) => {

        var user = {
            id: this.state.id,
            nume: this.state.nume,
            suma_donata: this.state.suma_donata
        }
        console.log('A caz was sent to update: ');
        console.log(user);
        console.log("handle update (state id)"+user.id);
        this.props.updateFunc(user);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleUpdate}>
                <label>
                    Id:
                    <input type="text" value={this.state.id} onChange={this.handleIdChange}/>
                </label><br/>
                <label>
                    Nume:
                    <input type="text" value={this.state.nume} onChange={this.handleNumeChange}/>
                </label><br/>
                <label>
                    Suma:
                    <input type="suma_donata" value={this.state.suma_donata} onChange={this.handleSumaChange}/>
                </label><br/>

                <input type="submit" value="Update"/>

            </form>
        );
    }
}
export default UpdateForm;