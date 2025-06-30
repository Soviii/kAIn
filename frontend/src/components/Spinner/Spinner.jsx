import loadingGIF from '../../assets/food-loading.gif';
import './Spinner.css';

const Spinner = () => (
    <div className="spinner-container" data-testid="spinner">
        <img src={loadingGIF} alt="Loading..." className="spinner" />
    </div>
);

export default Spinner;