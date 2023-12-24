pragma solidity ^0.8.0;

import "./MerkleDistributor.sol";

contract MerkleDistributorFactory {
    // Keep track of all created distributors
    MerkleDistributor[] public distributors;

    event DistributorCreated(address indexed distributorAddress,address tokenAddress,address owner,uint duration);

    function createDistributor(address token, bytes32 merkleRoot, uint _duration, address owner ) public {
        MerkleDistributor distributor = new MerkleDistributor(token, merkleRoot, _duration,msg.sender);
        distributors.push(distributor);
        emit DistributorCreated(address(distributor),token,owner,_duration);
    }

    function getDistributor(uint index) public view returns (MerkleDistributor) {
        require(index < distributors.length, "Index out of bounds");
        return distributors[index];
    }

    function getDistributorsCount() public view returns (uint) {
        return distributors.length;
    }
}
