package financial_management;


public class Price
{
  private int numberOfOpportunities;
  private int numberOfLikes;
  private static final double opportunityPrice = 0.1;
  private static final double likePrice = 2;
  private double payment;


  /** 建立一个给定购买like数量和opportunity数量的Price
    */
  public Price(int numberOfOpportunities, int numberOfLikes)
  {
    this.numberOfOpportunities = numberOfOpportunities;
    this.numberOfLikes = numberOfLikes;
    this.payment = priceFormula();
  }


  /** Return loanAmount */
  public double getPayment() {
    return payment;
  }


  /** Find total payment */
  public double priceFormula()
  {
    double totalPayment = numberOfOpportunities*opportunityPrice + numberOfLikes*likePrice;
    return totalPayment;    
  }

}
