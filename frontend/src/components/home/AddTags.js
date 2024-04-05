import React, { useState } from 'react';
import './AddTags.css';
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AddTags = () => {
    const existingTags = ['tag1', 'tag2', 'tag3'];
    const [tags, setTags] = useState(existingTags);
    const [newTag, setNewTag] = useState('');
    const [addedTags, setAddedTags] = useState([]);
    const [deletedTags, setDeletedTags] = useState([]);
    const notify = (message) => toast(message);

    const handleAddTag = () => {
        if (newTag.trim() !== '' && !tags.includes(newTag.trim()) && !addedTags.includes(newTag.trim())) {
            // setTags([...tags, newTag.trim()]);
            setAddedTags([...addedTags, newTag.trim()]);
            setNewTag('');
        }else if(addedTags.includes(newTag.trim())){
            notify(`Tag: ${newTag.trim()} already in to be added list !..`)
        }else{
            notify(`Tag: ${newTag.trim()} was already added !..`)
        }
    };

    const handleRemoveTag = (tagToRemove,type) => {
        if(type==='existing') {
            setTags(tags.filter(tag => tag !== tagToRemove));
            setDeletedTags([...deletedTags,tagToRemove]);
        }else if(type==='new') {
            setAddedTags(addedTags.filter(tag => tag !== tagToRemove));
        }else if(type==='delete'){
            setTags([...tags,tagToRemove]);
            setDeletedTags(deletedTags.filter(tag => tag !== tagToRemove));
        }
    };

    const handleConfirm = () => {
        console.log('Selected tags:', addedTags);
    };

    return (
        <div className="add-tags-container">
            <ToastContainer/>
            <h2 className="add-tags-title">Add Tags</h2>
            <div className="tag-list">
                {tags.map((tag, index) => (
                    <span key={index} className="tag">
                        {tag}
                        <button className="remove-tag-btn" onClick={() => handleRemoveTag(tag,"existing")}>x</button>
                    </span>
                ))}
            </div>
            <div className="input-container">
                <input
                    type="text"
                    value={newTag}
                    onChange={(e) => setNewTag(e.target.value)}
                    placeholder="Add a new tag"
                    className="tag-input"
                />
                <button className="add-tag-btn" onClick={handleAddTag}>Add Tag</button>
            </div>
            {addedTags.length>0&&<div className="added-tags-container">
                <h4>Tags to be Added:</h4>
                <div className="added-tag-list">
                    {addedTags.map((tag, index) => (
                        <span key={index} className="added-tag">
                            {tag}
                            <button className="remove-tag-btn" onClick={() => handleRemoveTag(tag,"new")}>x</button>
                        </span>
                    ))}
                </div>
            </div>}
            {deletedTags.length>0&&<div className="added-tags-container">
                <h4>Tags to be Deleted:</h4>
                <div className="added-tag-list">
                    {deletedTags.map((tag, index) => (
                        <span key={index} className="added-tag">
                            {tag}
                            <button className="remove-tag-btn" onClick={() => handleRemoveTag(tag,"delete")}>x</button>
                        </span>
                    ))}
                </div>
            </div>}
            <button className="confirm-btn" onClick={handleConfirm}>Confirm</button>
        </div>
    );
};

export default AddTags;