import React from 'react';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'

class NGO extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            ngos: [],
        };
    }

    componentDidMount() {
        fetch('http://localhost:8080/ngos?api-key=adminKey', {
            method: 'GET',
            headers: {
                'access-control-allow-origin': '*',
                'Content-type': 'application/json; charset=UTF-8',
            },
        })
            .then((res) => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        ngos: result.ngos
                    });
                },
                // Note: it's important to handle errors here
                // instead of a catch() block so that we don't swallow
                // exceptions from actual bugs in components.
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error,
                    });
                }
            );
    }

    render() {
        const {error, isLoaded, ngos} = this.state;
        if (error) {
            return <div>Error: {error.message}</div>;
        } else if (!isLoaded) {
            return <div>Loading...</div>;
        } else {
            return (
                <div className='list-group list-group-horizontal'>
                    {ngos.map((item) => (
                            <div className="card d-inline" style={{"width": "30rem", "margin": "10px"}}>
                                <img className="card-img-top" src={item.imageUrl} alt="Card image cap"
                                     />
                                <div className="card-body">
                                    <h5 className="card-title">{item.name}</h5>
                                    <p className="card-text">{item.description}</p>
                                </div>
                                <ul className="list-group list-group-flush">
                                    <li className="list-group-item">{item.location}</li>
                                    <li className="list-group-item">{item.address}</li>
                                    <li className="list-group-item">{item.founded}</li>
                                </ul>
                                <div className="card-body">
                                    <a href={item.website} className="card-link">Website</a>
                                    <a href={item.website} className="card-link">Support NGO!</a>
                                    <p></p>
                                    {item.issueAreaList.map((areas) => (
                                        <a href="#" className="card-link text-decoration-none text-muted"> {areas.replace('_',' ')} </a>
                                    ))}
                                </div>
                            </div>
                    ))}
                </div>
            );
        }
    }
}

export default NGO;
