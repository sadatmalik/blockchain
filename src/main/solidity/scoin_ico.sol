// S Coin ICO

// Version of compiler
//pragma solidity ^0.8.0;
pragma solidity ^0.4.11;

contract scoin_ico {

    // The maximum number of S coins available for sale
    uint public max_scoins = 1000000;

    // The USD to Scoin conversion rate
    uint public usd_to_scoins = 1000;

    // The total number of S coins that have been bought by investors
    uint public total_scoins_bought = 0;

    // Mapping from the investor address to the equity in S coins and USD
    mapping(address => uint) equity_scoins;
    mapping(address => uint) equity_usd;

    // Checking if an investor can buy S coins
    modifier can_buy_scoins(uint usd_invested) {
        require (usd_invested * usd_to_scoins + total_scoins_bought <= max_scoins);
        _;
    }

    // Getting the equity in S coins of an investor
    function equity_in_scoins(address investor) external constant returns (uint) {
        return equity_scoins[investor];
    }

    // Getting the equity in USD of an investor
    function equity_in_usd(address investor) external constant returns (uint) {
        return equity_usd[investor];
    }

    // Buying S coins
    function buy_scoins(address investor, uint usd_invested) external
    can_buy_scoins(usd_invested) {
        uint scoins_bought = usd_invested * usd_to_scoins;
        equity_scoins[investor] += scoins_bought;
        equity_usd[investor] = equity_scoins[investor] / usd_to_scoins;
        total_scoins_bought += scoins_bought;
    }

    // Selling S coins
    function sell_scoins(address investor, uint scoins_sold) external {
        equity_scoins[investor] -= scoins_sold;
        equity_usd[investor] = equity_scoins[investor] / usd_to_scoins;
        total_scoins_bought -= scoins_sold;
    }

    function scoin_ico(){

    }
}
