import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import { Login } from './components/Login/Login';
import { Register } from './components/Login/Register';
import { Home } from './components/Home/Home';
import { AllReimbs } from './components/Reimbursements/AllReimbs';
import { NewReimb } from './components/Reimbursements/NewReimb';
import { PendingReimbs } from './components/Reimbursements/PendingReimbs';
import { AllUsers } from './components/Users/AllUsers';
import { AllReimbsMan } from './components/Reimbursements/AllReimbsMan';
import { PendingMan } from './components/Reimbursements/PendingMan';
import { Profile } from './components/Users/Profile';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path='' element={<Login />}/>
          <Route path='/register' element={<Register />} />
          <Route path='/home' element={<Home />} />
          <Route path='/reimbs' element={<AllReimbs />} />
          <Route path='/new' element={<NewReimb />} />
          <Route path='/pending' element={<PendingReimbs />} />
          <Route path='/profile' element={<Profile />} />
          <Route path='/users' element={<AllUsers />} />
          <Route path='/man-reimbs' element={<AllReimbsMan />} />
          <Route path='/man-pending' element={<PendingMan />} />         
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
