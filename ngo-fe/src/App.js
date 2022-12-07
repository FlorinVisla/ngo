import './App.css';

import NGO from './components/NGO';
import Footer from './components/Footer'

import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link
} from "react-router-dom";

function App() {
    return (
        <div className='App'>
            <Router>
                <div>
                    <header>
                        <nav className="navbar navbar-expand-lg navbar-light bg-white">
                            <div className="container-fluid">
                                <button
                                    className="navbar-toggler"
                                    type="button"
                                    data-mdb-toggle="collapse"
                                    data-mdb-target="#navbarExample01"
                                    aria-controls="navbarExample01"
                                    aria-expanded="false"
                                    aria-label="Toggle navigation"
                                >
                                    <i className="fas fa-bars"></i>
                                </button>
                                <div className="collapse navbar-collapse d-flex" id="navbarExample01">
                                    <ul className="navbar-nav me-auto mb-2 mb-lg-0 justify-content-center list-inline mx-auto">
                                        <li className="nav-item active">
                                            <a className="nav-link" aria-current="page" href="#">Home</a>
                                        </li>
                                        <li className="nav-item">
                                            <a className="nav-link" href="#">Api</a>
                                        </li>
                                        <li className="nav-item">
                                            <a className="nav-link" href="#">Submit your NGO</a>
                                        </li>
                                        <li className="nav-item">
                                            <a className="nav-link" href="#">About</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </nav>
                    </header>


                    {/* A <Switch> looks through its children <Route>s and
                  renders the first one that matches the current URL. */}
                    <Routes>
                        <Route path="/" element={<NGO/>}>
                        </Route>
                    </Routes>
                    <Footer/>
                </div>
            </Router>
        </div>
    );
}

export default App;
