function CardMetrica({ titulo, valor }) {
  return (
    <div className="card-metrica">
      <h4>{titulo}</h4>
      <p>{valor}</p>
    </div>
  );
}

export default CardMetrica;