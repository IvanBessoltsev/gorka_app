import React, { useState } from 'react';
import axios from 'axios';
import classes from './FormDiscount.module.scss';

function FormDiscount() {

  const [numberCard, setNumberCard] = useState();
  const [currentLevel, setCurrentLevel] = useState();
  const [sizeDiscount, setSizeDiscount] = useState(0);
  const [totalAmount, setTotalAmount] = useState('00.00');
  const [finalDiscount, setFinalDiscount] = useState(0);
  const [refund, setRefund] = useState(0);

  const onCurrentCard = (event) => {
    setNumberCard(Number(event.target.value));
  };

  const dataDiscount = async (id) => {

    const activeInput = document.getElementById(id);
    const numericFormValue = Number(activeInput.value);
    const currentDate = new Date().toISOString().slice(0, 10);

    if (id === 'card') {
      const resLevel = await axios.get(`http://localhost:8080/api/app/currentLevel/${numericFormValue}`);
      const resDiscount = await axios.get(`http://localhost:8080/api/app/discount/${numericFormValue}/${currentDate}`);
      setCurrentLevel(resLevel.data.level);
      setSizeDiscount(resDiscount.data.discountByCard);
    }
    if (id === 'sum') {
      const resSum = await axios.post('http://localhost:8080/api/app/purchase', {
        cardNumber: numberCard,
        purchaseAmount: numericFormValue,
      });
      setTotalAmount(resSum.data.discountAmount);
      setFinalDiscount(resSum.data.finalDiscount);
    }
    if (id === 'refund') {
      const resRefund = await axios.post('http://localhost:8080/api/app/refund', {
        cardNumber: numberCard,
        purchaseAmount: numericFormValue,
      });
      setRefund(resRefund.data.refundAmount);
    }
  };

  return (
    <form className={classes.form} onSubmit={(event) => event.preventDefault()}>
      <div className={classes['form-container']}>
        <label htmlFor="card">Номер карты лояльности</label>
        <input id="card" type="text" placeholder="Введите номер карты лояльности" onChange={onCurrentCard} required />
        <button type="submit" onClick={() => dataDiscount('card')}>Текущий уровень и размер скидки</button>
        <p>
          Текущий уровень карты:
          {' '}
          {currentLevel}
          {' '}
          <br />
          Размер скидки:
          {`${sizeDiscount}%`}
        </p>

        <label htmlFor="sum">Сумма покупки</label>
        <input id="sum" type="tel" placeholder="Введите сумму покупки" />
        <button type="submit" onClick={() => dataDiscount('sum')}>Отправить</button>
        <p>
          Итоговая сумма:
          {totalAmount}
          {' '}
          <br />
          Сумма скидки:
          {finalDiscount}
        </p>

        <label htmlFor="refund">Сумма возврата</label>
        <input id="refund" type="tel" placeholder="Введите сумму возврата" />
        <button type="submit" onClick={() => dataDiscount('refund')}>Отправить</button>
        <p>
          Сумма возврата:
          {' '}
          {refund}
        </p>
      </div>
    </form>
  );
}

export default FormDiscount;
